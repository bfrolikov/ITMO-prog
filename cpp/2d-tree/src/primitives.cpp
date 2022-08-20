#include "primitives.h"

#include <algorithm>
#include <cmath>
#include <utility>

double Point::distance(const Point & point) const
{
    return sqrt((x() - point.x()) * (x() - point.x()) + (y() - point.y()) * (y() - point.y()));
}
Point::Point(double x, double y)
    : m_x(x)
    , m_y(y)
{
}
double Point::x() const
{
    return m_x;
}
double Point::y() const
{
    return m_y;
}
double Point::by_depth(std::size_t depth) const
{
    return depth % 2 == 0 ? x() : y();
}
std::ostream & operator<<(std::ostream & output, const Point & point)
{
    return output << "(" << point.x() << ", " << point.y() << ")";
}
bool Point::operator<(const Point & point) const
{
    if (y() == point.y()) {
        return x() < point.x();
    }
    return y() < point.y();
}
bool Point::operator==(const Point & point) const
{
    return x() == point.x() && y() == point.y();
}
bool Point::operator<=(const Point & point) const
{
    return operator<(point) || operator==(point);
}
bool Point::operator>=(const Point & point) const
{
    return !operator<(point);
}
bool Point::operator>(const Point & point) const
{
    return operator>=(point) && !operator==(point);
}
bool Point::operator!=(const Point & point) const
{
    return !operator==(point);
}
double Rect::xmin() const
{
    return m_left_bottom.x();
}
double Rect::ymin() const
{
    return m_left_bottom.y();
}
double Rect::xmax() const
{
    return m_right_top.x();
}
double Rect::ymax() const
{
    return m_right_top.y();
}
double Rect::distance(const Point & p) const
{
    if (contains(p)) {
        return 0;
    }
    if (xmin() <= p.x() && p.x() <= xmax()) {
        return std::min(std::abs(p.y() - ymin()), std::abs(p.y() - ymax()));
    }
    else if (ymin() <= p.y() && p.y() <= ymax()) {
        return std::min(std::abs(p.x() - xmin()), std::abs(p.x() - xmax()));
    }
    double top_left = p.distance(Point(xmin(), ymax()));
    double top_right = p.distance(m_right_top);
    double bottom_right = p.distance(Point(xmax(), ymin()));
    double bottom_left = p.distance(m_left_bottom);
    return std::min({top_left, top_right, bottom_right, bottom_left});
}
bool Rect::contains(const Point & p) const
{
    return xmin() <= p.x() && p.x() <= xmax() && ymin() <= p.y() && p.y() <= ymax();
}
bool Rect::contains(const Rect & rect) const
{
    return xmin() <= rect.xmin() && rect.xmax() <= xmax() && ymin() <= rect.ymin() && rect.ymax() <= ymax();
}
bool Rect::intersects(const Rect & rect) const
{
    bool tmp1 = xmax() < rect.xmin() || rect.xmax() < xmin();
    bool tmp2 = ymax() < rect.ymin() || rect.ymax() < ymin();
    return !tmp1 && !tmp2;
}
Rect::Rect(const Point & left_bottom, const Point & right_top)
    : m_left_bottom(left_bottom)
    , m_right_top(right_top)
{
}
CommonIterator::CommonIterator()
    : m_points(std::make_shared<std::vector<Point>>())
    , m_it(m_points->begin())
{
}
CommonIterator::CommonIterator(std::shared_ptr<std::vector<Point>> points, bool end)
    : m_points(std::move(points))
    , m_it(end ? m_points->end() : m_points->begin())
{
}
CommonIterator::reference CommonIterator::operator*() const
{
    return *m_it;
}
CommonIterator::pointer CommonIterator::operator->() const
{
    return &(*m_it);
}
CommonIterator & CommonIterator::operator++()
{
    ++m_it;
    return *this;
}
CommonIterator CommonIterator::operator++(int)
{
    auto tmp = *this;
    operator++();
    return tmp;
}
bool operator==(const CommonIterator & lhs, const CommonIterator & rhs)
{
    return lhs.m_it == rhs.m_it;
}
bool operator!=(const CommonIterator & lhs, const CommonIterator & rhs)
{
    return !(lhs == rhs);
}
