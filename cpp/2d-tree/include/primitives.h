#pragma once

#include <iostream>
#include <limits>
#include <memory>
#include <optional>
#include <random>
#include <set>
#include <utility>
#include <variant>
#include <vector>

class Point
{
public:
    Point(double x, double y);

    double x() const;
    double y() const;
    double distance(const Point &) const;
    double by_depth(std::size_t) const;

    bool operator<(const Point &) const;
    bool operator>(const Point &) const;
    bool operator<=(const Point &) const;
    bool operator>=(const Point &) const;
    bool operator==(const Point &) const;
    bool operator!=(const Point &) const;

    friend std::ostream & operator<<(std::ostream &, const Point &);

private:
    double m_x;
    double m_y;
};

class Rect
{
public:
    Rect(const Point &, const Point &);

    double xmin() const;
    double ymin() const;
    double xmax() const;
    double ymax() const;
    double distance(const Point & p) const;

    bool contains(const Point & p) const;
    bool contains(const Rect &) const;
    bool intersects(const Rect &) const;

private:
    Point m_left_bottom;
    Point m_right_top;
};

namespace rbtree {
class PointSet;
}
namespace kdtree {
class PointSet;
}

class CommonIterator
{
    friend class rbtree::PointSet;
    friend class kdtree::PointSet;
    std::shared_ptr<std::vector<Point>> m_points;
    std::vector<Point>::iterator m_it;
    CommonIterator(std::shared_ptr<std::vector<Point>>, bool end = false);

public:
    using iterator_category = std::forward_iterator_tag;
    using value_type = Point;
    using pointer = const value_type *;
    using reference = const value_type &;
    using difference_type = std::ptrdiff_t;
    CommonIterator();
    reference operator*() const;
    pointer operator->() const;
    CommonIterator & operator++();
    CommonIterator operator++(int);
    friend bool operator==(const CommonIterator &, const CommonIterator &);
    friend bool operator!=(const CommonIterator &, const CommonIterator &);
};
namespace rbtree {

class PointSet
{

    std::set<Point> m_points;
    std::shared_ptr<std::vector<Point>> m_shared_points;

public:
    using iterator = CommonIterator;
    PointSet(const std::string & filename = {});
    bool empty() const;
    std::size_t size() const;
    void put(const Point &);
    bool contains(const Point &) const;

    // second iterator points to an element out of range
    std::pair<iterator, iterator> range(const Rect &) const;
    iterator begin() const;
    iterator end() const;

    std::optional<Point> nearest(const Point &) const;
    // second iterator points to an element out of range
    std::pair<iterator, iterator> nearest(const Point &, std::size_t) const;

    friend std::ostream & operator<<(std::ostream &, const PointSet &);
};

} // namespace rbtree
namespace kdtree {

class PointSet
{
public:
    using iterator = CommonIterator;

    PointSet(const std::string & filename = {});
    bool empty() const;
    std::size_t size() const;
    void put(const Point &);
    bool contains(const Point &) const;

    std::pair<iterator, iterator> range(const Rect &) const;
    iterator begin() const;
    iterator end() const;

    std::optional<Point> nearest(const Point &) const;
    std::pair<iterator, iterator> nearest(const Point &, std::size_t) const;

    friend std::ostream & operator<<(std::ostream &, const PointSet &);

private:
    class KMinSet
    {
        std::set<std::pair<double, Point>> m_set;
        Point m_reference;
        std::size_t m_k;

    public:
        using iterator = std::set<std::pair<double, Point>>::iterator;
        KMinSet(const Point &, std::size_t);
        void insert(const Point &);
        double max_dist() const;
        bool full() const;
        iterator begin() const;
        iterator end() const;
    };
    struct Node;
    using Ptr = std::shared_ptr<Node>;
    struct Node
    {
        Ptr left = nullptr;
        Ptr right = nullptr;
        std::size_t subtree_size = 1;
        Point node_point;
        Rect area;
        Node(const Point & nodePoint, const Rect & area);
        void recalc();
    };
    Ptr m_root;
    mutable std::shared_ptr<std::vector<Point>> m_shared_points;
    std::size_t subtree_size(const Ptr &) const;
    Ptr put_impl(Ptr, const Point &, Point, Point, std::size_t);
    bool contains_impl(const Ptr &, const Point &, std::size_t) const;
    void nearest_impl(const Ptr &, const Point &, std::optional<Point> &, double &, std::size_t) const;
    void k_nearest_impl(const Ptr &, const Point &, KMinSet &, std::size_t) const;
    void range_impl(const Ptr &, const Rect &, const std::shared_ptr<std::vector<Point>> &) const;
    void put_subtree(const Ptr &, const std::shared_ptr<std::vector<Point>> &) const;
    void print(const Ptr &, std::ostream &) const;
    void split_and_add(std::vector<Point> & points, std::size_t, std::size_t, std::size_t);
    iterator get_iterator(bool end = false) const;
    void check_subtree_range(const Ptr &, const Rect &, const std::shared_ptr<std::vector<Point>> &) const;
    std::pair<Ptr, Ptr> get_branches(const Ptr &, const Point &, std::size_t) const;
    double INF = std::numeric_limits<double>::infinity();
};

} // namespace kdtree
