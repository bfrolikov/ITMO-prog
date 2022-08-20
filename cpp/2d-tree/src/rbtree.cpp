#include "primitives.h"

#include <fstream>
namespace rbtree {

bool PointSet::empty() const
{
    return m_points.empty();
}
std::size_t PointSet::size() const
{
    return m_points.size();
}
void PointSet::put(const Point & point)
{
    if (!contains(point)) {
        m_points.insert(point);
        m_shared_points->push_back(point);
    }
}
bool PointSet::contains(const Point & point) const
{
    return m_points.count(point) != 0;
}
std::pair<PointSet::iterator, PointSet::iterator> PointSet::range(const Rect & rect) const
{
    auto tmp = std::make_shared<std::vector<Point>>();
    for (const Point & p : m_points) {
        if (rect.contains(p)) {
            tmp->push_back(p);
        }
    }
    return {iterator(tmp), iterator(tmp, true)};
}
PointSet::iterator PointSet::begin() const
{
    return iterator(m_shared_points);
}
PointSet::iterator PointSet::end() const
{
    return iterator(m_shared_points, true);
}
std::optional<Point> PointSet::nearest(const Point & point) const
{
    if (empty()) {
        return {};
    }
    Point curr_nearest = *m_points.begin();
    for (const Point & p : m_points) {
        if (p.distance(point) < curr_nearest.distance(point)) {
            curr_nearest = p;
        }
    }
    return curr_nearest;
}
std::pair<PointSet::iterator, PointSet::iterator> PointSet::nearest(const Point & point, std::size_t k) const
{
    if (empty()) {
        return {iterator(), iterator()};
    }
    auto k_nearest = std::make_shared<std::vector<Point>>();
    auto cmp = [&point](const Point & lhs, const Point & rhs) {
        return point.distance(lhs) <= point.distance(rhs);
    };
    std::set<Point, decltype(cmp)> k_nearest_set(cmp);
    for (const Point & p : m_points) {
        k_nearest_set.insert(p);
    }
    auto it = k_nearest_set.begin();
    for (std::size_t i = 0; i < k && it != k_nearest_set.end(); ++i, ++it) {
        k_nearest->push_back(*it);
    }
    return {iterator(k_nearest), iterator(k_nearest, true)};
}
std::ostream & operator<<(std::ostream & out, const PointSet & set)
{
    for (const Point & p : set.m_points) {
        out << p << " ";
    }
    return out;
}
PointSet::PointSet(const std::string & filename)
    : m_shared_points(std::make_shared<std::vector<Point>>())
{
    std::ifstream in(filename);
    double x;
    double y;
    while (in >> x >> y) {
        put(Point(x, y));
    }
    in.close();
}
} // namespace rbtree