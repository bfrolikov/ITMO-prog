#include "primitives.h"

#include <algorithm>
#include <fstream>
namespace kdtree {
kdtree::PointSet::Node::Node(const Point & nodePoint, const Rect & area)
    : node_point(nodePoint)
    , area(area)
{
}
void PointSet::Node::recalc()
{
    std::size_t subtree_sz = 1;
    if (left != nullptr) {
        subtree_sz += left->subtree_size;
    }
    if (right != nullptr) {
        subtree_sz += right->subtree_size;
    }
    subtree_size = subtree_sz;
}
std::size_t PointSet::size() const
{
    return subtree_size(m_root);
}
bool PointSet::empty() const
{
    return size() == 0;
}
void PointSet::put(const Point & point)
{
    if (!contains(point)) {
        m_root = put_impl(m_root, point, Point(-INF, -INF), Point(INF, INF), 0);
    }
}
PointSet::Ptr PointSet::put_impl(PointSet::Ptr curr, const Point & point, Point left_bottom, Point right_top, std::size_t depth)
{
    if (curr == nullptr) {
        return std::make_shared<Node>(point, Rect(left_bottom, right_top));
    }
    bool even = depth % 2 == 0;
    Point left_mid(
            even ? curr->node_point.x() : left_bottom.x(),
            even ? left_bottom.y() : curr->node_point.y());
    Point right_mid(
            even ? curr->node_point.x() : right_top.x(),
            even ? right_top.y() : curr->node_point.y());
    if (point.by_depth(depth) <= curr->node_point.by_depth(depth)) {
        curr->left = put_impl(curr->left, point, left_bottom, right_mid, depth + 1);
    }
    else {
        curr->right = put_impl(curr->right, point, left_mid, right_top, depth + 1);
    }
    curr->recalc();
    return curr;
}

std::size_t PointSet::subtree_size(const PointSet::Ptr & curr) const
{
    return (curr == nullptr ? 0 : curr->subtree_size);
}
bool PointSet::contains_impl(const PointSet::Ptr & curr, const Point & point, std::size_t depth) const
{
    if (curr == nullptr) {
        return false;
    }
    if (curr->node_point == point) {
        return true;
    }
    else if (point.by_depth(depth) < curr->node_point.by_depth(depth)) {
        return contains_impl(curr->left, point, depth + 1);
    }
    return contains_impl(curr->right, point, depth + 1);
}
void PointSet::nearest_impl(const PointSet::Ptr & curr, const Point & point, std::optional<Point> & nearest_point, double & min_distance, std::size_t depth) const
{
    if (curr == nullptr) {
        return;
    }
    auto [target, other] = get_branches(curr, point, depth);
    nearest_impl(target, point, nearest_point, min_distance, depth + 1);
    if (point.distance(curr->node_point) < min_distance) {
        min_distance = point.distance(curr->node_point);
        nearest_point = curr->node_point;
    }
    if (other != nullptr && other->area.distance(point) <= min_distance) {
        nearest_impl(other, point, nearest_point, min_distance, depth + 1);
    }
}
void PointSet::k_nearest_impl(const PointSet::Ptr & curr, const Point & point, PointSet::KMinSet & min_set, std::size_t depth) const
{
    if (curr == nullptr) {
        return;
    }
    auto [target, other] = get_branches(curr, point, depth);
    k_nearest_impl(target, point, min_set, depth);
    min_set.insert(curr->node_point);
    if (other != nullptr && (!min_set.full() || other->area.distance(point) <= min_set.max_dist())) {
        k_nearest_impl(other, point, min_set, depth + 1);
    }
}
PointSet::iterator PointSet::begin() const
{
    return get_iterator();
}
PointSet::iterator PointSet::end() const
{
    return get_iterator(true);
}
void PointSet::range_impl(const PointSet::Ptr & curr, const Rect & rect, const std::shared_ptr<std::vector<Point>> & range_vector) const
{
    if (curr == nullptr) {
        return;
    }
    if (rect.contains(curr->node_point)) {
        range_vector->push_back(curr->node_point);
    }
    check_subtree_range(curr->left, rect, range_vector);
    check_subtree_range(curr->right, rect, range_vector);
}
bool PointSet::contains(const Point & point) const
{
    return contains_impl(m_root, point, 0);
}
std::pair<PointSet::iterator, PointSet::iterator> PointSet::range(const Rect & rect) const
{
    auto tmp = std::make_shared<std::vector<Point>>();
    range_impl(m_root, rect, tmp);
    return {iterator(tmp), iterator(tmp, true)};
}
void PointSet::put_subtree(const PointSet::Ptr & curr, const std::shared_ptr<std::vector<Point>> & range_vector) const
{
    if (curr == nullptr) {
        return;
    }
    range_vector->push_back(curr->node_point);
    put_subtree(curr->left, range_vector);
    put_subtree(curr->right, range_vector);
}
std::optional<Point> PointSet::nearest(const Point & point) const
{
    std::optional<Point> nearest_point{};
    double min_distance = INF;
    nearest_impl(m_root, point, nearest_point, min_distance, 0);
    return nearest_point;
}
std::pair<PointSet::iterator, PointSet::iterator> PointSet::nearest(const Point & point, std::size_t k) const
{
    auto k_nearest = std::make_shared<std::vector<Point>>();
    if (k == 0) {
        return {iterator(k_nearest, true), iterator(k_nearest, true)};
    }
    KMinSet min_set(point, k);
    k_nearest_impl(m_root, point, min_set, 0);
    for (const auto & p : min_set) {
        k_nearest->push_back(p.second);
    }
    return {iterator(k_nearest), iterator(k_nearest, true)};
}
std::ostream & operator<<(std::ostream & out, const PointSet & set)
{
    set.print(set.m_root, out);
    return out;
}
void PointSet::print(const Ptr & curr, std::ostream & out) const
{
    if (curr == nullptr) {
        return;
    }
    out << curr->node_point << " ";
    print(curr->left, out);
    print(curr->right, out);
}
PointSet::PointSet(const std::string & filename)
    : m_root(nullptr)
    , m_shared_points(std::make_shared<std::vector<Point>>())
{
    std::vector<Point> tmp;
    std::ifstream in(filename);
    double x;
    double y;
    while (in >> x >> y) {
        tmp.emplace_back(x, y);
    }
    if (!tmp.empty()) {
        split_and_add(tmp, 0, tmp.size(), 0);
    }
    in.close();
}
void PointSet::split_and_add(std::vector<Point> & points, std::size_t l, std::size_t r, std::size_t depth)
{
    if (r - l == 1) {
        put(points[l]);
        return;
    }
    auto cmp = [&depth](const Point & lhs, const Point & rhs) {
        return lhs.by_depth(depth) <= rhs.by_depth(depth);
    };
    auto begin = points.begin();
    std::size_t m = l + (r - l) / 2;
    std::nth_element(begin + l, begin + m, begin + r, cmp);
    put(points[m]);
    split_and_add(points, l, m, depth + 1);
    if (m + 1 < r) {
        split_and_add(points, m + 1, r, depth + 1);
    }
}
std::pair<PointSet::Ptr, PointSet::Ptr> PointSet::get_branches(const PointSet::Ptr & curr, const Point & point, std::size_t depth) const
{
    if (point.by_depth(depth) <= curr->node_point.by_depth(depth)) {
        return {curr->left, curr->right};
    }
    return {curr->right, curr->left};
}

void PointSet::check_subtree_range(const PointSet::Ptr & curr, const Rect & rect, const std::shared_ptr<std::vector<Point>> & range_vector) const
{
    if (curr != nullptr) {
        if (rect.contains(curr->area)) {
            put_subtree(curr, range_vector);
        }
        else if (rect.intersects(curr->area)) {
            range_impl(curr, rect, range_vector);
        }
    }
}
PointSet::iterator PointSet::get_iterator(bool end) const
{
    if (m_shared_points->size() < size()) {
        put_subtree(m_root, m_shared_points);
    }
    return iterator(m_shared_points, end);
}

PointSet::KMinSet::KMinSet(const Point & reference, std::size_t k)
    : m_reference(reference)
    , m_k(k)
{
}
void PointSet::KMinSet::insert(const Point & point)
{
    double dist = m_reference.distance(point);
    if (m_set.size() < m_k) {
        m_set.insert({dist, point});
    }
    else if (dist < max_dist()) {
        m_set.erase(std::prev(m_set.end()));
        m_set.insert({dist, point});
    }
}
double PointSet::KMinSet::max_dist() const
{
    return m_set.rbegin()->first;
}
bool PointSet::KMinSet::full() const
{
    return m_set.size() == m_k;
}
PointSet::KMinSet::iterator PointSet::KMinSet::begin() const
{
    return m_set.begin();
}
PointSet::KMinSet::iterator PointSet::KMinSet::end() const
{
    return m_set.end();
}

} // namespace kdtree