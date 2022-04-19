package com.pantifik.ds.list.arraylist;

import com.pantifik.ds.list.AbstractList;
import com.pantifik.ds.list.utils.ListUtils;
import java.util.List;
import java.util.Objects;

class Sublist<E> extends AbstractList<E> {

  final int offset;
  private final AbstractList<E> list;
  int size;

  public Sublist(AbstractList<E> list, int fromIndex, int toIndex) {
    Objects.requireNonNull(list);
    Objects.checkFromToIndex(fromIndex, toIndex, list.size());
    this.list = list;
    this.offset = fromIndex;
    this.size = toIndex - fromIndex;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public E get(int index) {
    Objects.checkIndex(index, size);
    return list.get(index + offset);
  }

  @Override
  public E set(int index, E element) {
    Objects.checkIndex(index, size);
    return list.set(index + offset, element);
  }

  @Override
  public void add(int index, E element) {
    ListUtils.checkIndexInclusiveLength(index, size);
    list.add(index + offset, element);
    size++;
  }

  @Override
  public E remove(int index) {
    Objects.checkIndex(index, size);
    E data = list.remove(index + offset);
    size--;
    return data;
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    return new Sublist<>(this, fromIndex, toIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, size, list);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (!(o instanceof Sublist<?> sublist)) {return false;}
    return offset == sublist.offset && size == sublist.size && Objects.equals(
        list, sublist.list);
  }

  @Override
  public void removeRange(int fromIndex, int toIndex) {
    list.removeRange(offset + fromIndex, offset + toIndex);
    size = 0;
  }
}
