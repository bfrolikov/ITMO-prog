"use strict";
const {
    Const,
    Variable,
    Add,
    Subtract,
    Multiply,
    Divide,
    Negate,
    Cube,
    Cbrt,
    parse
} = (function () {


    function Expression(...args) {
        this.args = args;
    }

    Expression.prototype = {
        evaluate: function (...vars) {
            return this.operation(...this.args.map(it => it.evaluate(...vars)))
        },
        toString: function () {
            return this.args.join(" ") + " " + this.representation;
        },
        diff: function (dv) {
            return this.diffOperation(dv, ...this.args)
        }
    }

    function getExpression(operation, representation, diffOperation) {
        function CurrentExpr(...args) {
            Expression.call(this, ...args);
        }

        CurrentExpr.prototype = Object.create(Expression.prototype);
        CurrentExpr.prototype.operation = operation;
        CurrentExpr.prototype.diffOperation = diffOperation;
        CurrentExpr.prototype.representation = representation;
        CurrentExpr.prototype.getArity = () => operation.length;
        CurrentExpr.prototype.constructor = CurrentExpr;
        return CurrentExpr;
    }

    function getFunction(operation, representation, diffOperation) {
        return getExpression(x => operation(x), representation,
            (dv, x) => new Multiply(diffOperation(dv, x), x.diff(dv))
        )
    }


    const {Const, getConst} = (function () {
        let consts = new Map();

        function Const(value) {
            this.evaluate = () => value;
            this.toString = () => "" + value;
        }

        Const.prototype = {
            diff: function () {
                return getConst(0);
            }
        }
        const getConst = value => {
            let temp = consts.get(value);
            if (temp === undefined) {
                temp = new Const(value);
                consts.set(value, temp);
            }
            return temp;
        };
        return {Const, getConst};
    })();

    const {Variable, getVariable} = (function () {
        const variables = new Map([
            ["x", [new Variable("x"), 0]],
            ["y", [new Variable("y"), 1]],
            ["z", [new Variable("z"), 2]]
        ]);

        function Variable(name) {
            this.evaluate = (...vars) => vars[variables.get(name)[1]];
            this.toString = () => name;
            this.diff = dv => dv === name ? getConst(1) : getConst(0);
        }

        return {
            Variable,
            getVariable: name => {
                const temp = variables.get(name)
                if (temp === undefined) {
                    return temp;
                }
                return temp[0];
            }
        }
    })();


    const Add = getExpression(
        (x, y) => x + y,
        "+",
        (dv, x, y) => new Add(x.diff(dv), y.diff(dv))
    );

    const Subtract = getExpression(
        (x, y) => x - y,
        "-",
        (dv, x, y) => new Subtract(x.diff(dv), y.diff(dv))
    );

    const Multiply = getExpression(
        (x, y) => x * y,
        "*",
        (dv, x, y) => new Add(new Multiply(x.diff(dv), y), new Multiply(x, y.diff(dv)))
    );

    const Divide = getExpression(
        (x, y) => x / y,
        "/",
        (dv, x, y) => new Divide(
            new Subtract(
                new Multiply(x.diff(dv), y),
                new Multiply(x, y.diff(dv))
            ),
            new Multiply(y, y)
        )
    );

    const Negate = getExpression(
        x => -x,
        "negate",
        (dv, x) => new Negate(x.diff(dv))
    );

    const Cube = getFunction(
        x => x * x * x,
        "cube",
        (dv, x) =>
            new Multiply(
                getConst(3),
                new Multiply(x, x)
            )
    );

    const Cbrt = getFunction(
        x => Math.cbrt(x),
        "cbrt",
        (dv, x) =>
            new Divide(
                getConst(1),
                new Multiply(
                    getConst(3),
                    new Cbrt(new Multiply(x, x))
                )
            )
    );

    const operationSymbolMap = new Map([
        ["+", Add],
        ["-", Subtract],
        ["*", Multiply],
        ["/", Divide],
        ["negate", Negate],
        ["cube", Cube],
        ["cbrt", Cbrt]
    ]);

    const parse = (expression) => {
        let stack = [];
        let tokens = expression.trim().split(/\s+/);
        for (const token of tokens) {
            const v = getVariable(token);
            if (v !== undefined) {
                stack.push(v)
            } else {
                const Operation = operationSymbolMap.get(token);
                if (Operation !== undefined) {
                    stack.push(new Operation(...stack.splice(-Operation.prototype.getArity())));
                } else {
                    stack.push(getConst(+token));
                }
            }
        }
        return stack[0];
    }

    return {
        Const,
        Variable,
        Add,
        Subtract,
        Multiply,
        Divide,
        Negate,
        Cube,
        Cbrt,
        parse
    }
})();