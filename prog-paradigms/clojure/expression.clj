(load-file "proto.clj")

(def constant constantly)
(defn variable [name]
  (fn [vars] (vars name)))
(defn expression [f]
  (fn [& operands]
    (fn [vars]
      (apply f (map #(% vars) operands)))))

(def add (expression +))
(def subtract (expression -))
(def multiply (expression *))
(def negate (expression #(- %)))
(def divide (expression (fn
                          ([operand] (/ 1.0 operand))
                          ([operand & other] (/ (double operand) (apply * other))))))
(def sum add)
(def avg (expression (fn [& operands] (/ (apply + operands) (count operands)))))

(def operation-symbols
  {'+      add
   '-      subtract
   '*      multiply
   '/      divide
   'negate negate
   'sum    sum
   'avg    avg
   })



;START OF OBJECT

(def evaluate (method :evaluate))
(def operation (field :operation))
(def arguments (field :arguments))
(def value (field :value))
(def var-name (field :var-name))
(def representation (field :representation))
(def toString (method :toString))
(def diff-operation (field :diff-operation))
(def diff (method :diff))
(defn Expression [this arguments]
  (assoc this
    :arguments arguments))
(def ExpressionPrototype
  {
   :evaluate (fn [this vars] (apply (operation this) (map #(evaluate % vars) (arguments this))))
   :toString (fn [this] (format "(%s %s)" (representation this)
                                (clojure.string/join " " (map #(toString %) (arguments this)))))
   :diff     (fn [this dv]
               (apply
                 (apply (partial (diff-operation this)) (arguments this))
                 (map #(diff % dv) (arguments this))
                 ))
   })
;
(defn get-expression [operation representation diff-operation]
  (let [CurrentExprPrototype
        {:prototype      ExpressionPrototype
         :operation      operation
         :representation representation
         :diff-operation diff-operation}
        CurrentExpr (fn [this & arguments] (Expression this arguments))
        ]
    (constructor CurrentExpr CurrentExprPrototype)
    ))

(declare Constant)

(defn ConstConstructor [this value]
  (assoc this
    :value value))
(def ConstPrototype
  {
   :evaluate (fn [this _] (value this))
   :toString (fn [this] (str (value this)))
   :diff     (fn [_ _] (Constant 0))
   })
(def Constant (constructor ConstConstructor ConstPrototype))


(defn VariableConstructor [this var-name]
  (assoc this
    :var-name var-name))
(def VariablePrototype
  {
   :evaluate (fn [this vars] (vars (var-name this)))
   :toString (fn [this] (var-name this))
   :diff     (fn [this dv]
               (cond
                 (= (var-name this) dv) (Constant 1)
                 :else (Constant 0)
                 ))
   })
(def Variable (constructor VariableConstructor VariablePrototype))

(def Negate (get-expression - 'negate
    (fn [_]
      (fn [dx] (Negate dx)))
    ))
(def Add
  (get-expression
    +
    '+
    (fn [& _]
      (fn [& d-args] (apply Add d-args)))
    ))
(def Subtract
  (get-expression
    -
    '-
    (fn [& _]
      (fn [& d-args] (apply Subtract d-args)))
    ))
(defn Multiply [& _] ())
(defn multiply-derivative [args d-args n]
  (cond
    (== n 1) (first d-args)
    :else (let [rst' (multiply-derivative (rest args) (rest d-args) (dec n))]
            (Add
              (apply Multiply (first d-args) (rest args))
              (Multiply (first args) rst')
              )
            )
    ))
(def Multiply
  (get-expression
    *
    '*
    (fn [& args]
      (fn [& d-args] (multiply-derivative args d-args (count d-args))))
    ))
(defn Divide [& _] ())
(defn divide-derivative [args d-args]
  (cond
    (== (count args) 1) (Negate (Divide (first d-args) (Multiply (first args) (first args))))
    :else (let [denom' (multiply-derivative (rest args) (rest d-args) (dec (count args)))]
            (Divide
              (Subtract
                (apply Multiply (first d-args) (rest args))
                (Multiply denom' (first args))
                )
              (Multiply (apply Multiply (rest args)) (apply Multiply (rest args)))
              ))
    ))
(def Divide
  (get-expression
    (fn
      ([operand] (/ 1.0 operand))
      ([operand & other] (/ (double operand) (apply * other))))
    '/
    (fn [& args]
      (fn [& d-args]
        (divide-derivative args d-args)))
    ))
(def Sum
  (get-expression
    +
    'sum
    (fn [& _]
      (fn [& d-args] (apply Sum d-args)))
    ))
(def Avg
  (get-expression
    (fn [& operands] (/ (apply + operands) (count operands)))
    'avg
    (fn [& args]
      (fn [& d-args] (Divide (apply Add d-args) (Constant (count args)))))
    ))

(def obj-operation-symbols
  {
   '+      Add
   '-      Subtract
   '*      Multiply
   '/      Divide
   'negate Negate
   'sum    Sum
   'avg    Avg
   })



(defn parse-impl [cnst varb symb-map curr]
  (cond
    (number? curr) (cnst curr)
    (list? curr) (apply (symb-map (first curr)) (map (partial parse-impl cnst varb symb-map) (rest curr)))
    (symbol? curr) (varb (str curr))
    ))


(def parseObject (comp (partial parse-impl Constant Variable obj-operation-symbols) read-string))
(def parseFunction (comp (partial parse-impl constant variable operation-symbols) read-string))
