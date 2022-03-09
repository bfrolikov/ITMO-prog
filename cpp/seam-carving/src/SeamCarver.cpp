#include "SeamCarver.h"

#include <cmath>
#include <limits>
#include <vector>

namespace {
int sq(int x)
{
    return x * x;
}
int delta(const Image::Pixel & first, const Image::Pixel & second)
{
    return sq(first.m_red - second.m_red) + sq(first.m_green - second.m_green) + sq(first.m_blue - second.m_blue);
}
} // anonymous namespace

SeamCarver::SeamCarver(Image image)
    : m_image(std::move(image))
{
}

const Image & SeamCarver::GetImage() const
{
    return m_image;
}

size_t SeamCarver::GetImageWidth() const
{
    return m_image.m_table.size();
}

size_t SeamCarver::GetImageHeight() const
{
    return m_image.m_table[0].size();
}

double SeamCarver::GetPixelEnergy(size_t columnId, size_t rowId) const
{
    const size_t width = GetImageWidth();
    const size_t height = GetImageHeight();
    const Image::Pixel left = m_image.GetPixel((columnId + width - 1) % width, rowId);
    const Image::Pixel right = m_image.GetPixel((columnId + 1) % width, rowId);
    const Image::Pixel top = m_image.GetPixel(columnId, (rowId + height - 1) % height);
    const Image::Pixel bottom = m_image.GetPixel(columnId, (rowId + 1) % height);
    return sqrt(static_cast<double>(delta(right, left) + delta(bottom, top)));
}

SeamCarver::Seam SeamCarver::FindHorizontalSeam() const
{
    return FindSeam(GetImageWidth(), GetImageHeight(), false);
}

SeamCarver::Seam SeamCarver::FindVerticalSeam() const
{
    return FindSeam(GetImageHeight(), GetImageWidth(), true);
}

void SeamCarver::RemoveHorizontalSeam(const Seam & seam)
{
    const size_t width = GetImageWidth();
    for (size_t columnId = 0; columnId < width; columnId++) {
        m_image.m_table[columnId].erase(m_image.m_table[columnId].begin() + seam[columnId]);
    }
}

void SeamCarver::RemoveVerticalSeam(const Seam & seam)
{
    const size_t height = GetImageHeight();
    const size_t width = GetImageWidth();
    for (size_t rowId = 0; rowId < height; rowId++) {
        for (size_t columnId = seam[rowId]; columnId + 1 < width; columnId++) {
            m_image.m_table[columnId][rowId] = m_image.m_table[columnId + 1][rowId];
        }
    }
    m_image.m_table.pop_back();
}
SeamCarver::Seam SeamCarver::FindSeam(size_t dim1Size, size_t dim2Size, bool vertical) const
{
    std::vector<std::vector<PathNode>> pathMatrix(dim1Size, std::vector<PathNode>(dim2Size));
    for (size_t dim2Id = 0; dim2Id < dim2Size; dim2Id++) {
        pathMatrix[0][dim2Id].minSum = vertical ? GetPixelEnergy(dim2Id, 0) : GetPixelEnergy(0, dim2Id);
    }
    for (size_t dim1Id = 1; dim1Id < dim1Size; dim1Id++) {
        for (size_t dim2Id = 0; dim2Id < dim2Size; dim2Id++) {
            double minAncestorSum = pathMatrix[dim1Id - 1][dim2Id].minSum;
            size_t ancestorIndex = dim2Id;
            if (dim2Id > 0 && pathMatrix[dim1Id - 1][dim2Id - 1].minSum < minAncestorSum) {
                minAncestorSum = pathMatrix[dim1Id - 1][dim2Id - 1].minSum;
                ancestorIndex = dim2Id - 1;
            }
            if (dim2Id < dim2Size - 1 && pathMatrix[dim1Id - 1][dim2Id + 1].minSum < minAncestorSum) {
                minAncestorSum = pathMatrix[dim1Id - 1][dim2Id + 1].minSum;
                ancestorIndex = dim2Id + 1;
            }
            pathMatrix[dim1Id][dim2Id] = {ancestorIndex, minAncestorSum};
            pathMatrix[dim1Id][dim2Id].minSum += vertical ? GetPixelEnergy(dim2Id, dim1Id) : GetPixelEnergy(dim1Id, dim2Id);
        }
    }
    size_t currentDim2Id = 0;
    double bottomMinimumSum = pathMatrix[dim1Size - 1][currentDim2Id].minSum;
    for (size_t dim2Id = 0; dim2Id < dim2Size; dim2Id++) {
        if (pathMatrix[dim1Size - 1][dim2Id].minSum < bottomMinimumSum) {
            bottomMinimumSum = pathMatrix[dim1Size - 1][dim2Id].minSum;
            currentDim2Id = dim2Id;
        }
    }
    Seam seam(dim1Size);
    size_t dim1Id = dim1Size - 1;
    while (true) {
        seam[dim1Id] = currentDim2Id;
        currentDim2Id = pathMatrix[dim1Id][currentDim2Id].ancestorIndex;
        if (dim1Id == 0) {
            break;
        }
        dim1Id--;
    }
    return seam;
}
