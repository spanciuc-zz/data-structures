package com.pantifik.ds.list;

import static com.pantifik.ds.list.utils.ListUtils.resolveEqualsPredicate;
import com.pantifik.ds.list.utils.ListToArrayConverter;
import com.pantifik.ds.list.utils.ListUtils;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Predicate;

public abstract class AbstractList<E> implements List<E> {

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o) != -1;
  }

  @Override
  public Iterator<E> iterator() {
    return new IteratorImpl();
  }

  @Override
  public Object[] toArray() {
    return ListToArrayConverter.convert(this);
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return ListToArrayConverter.convert(this, a);
  }

  @Override
  public boolean add(E e) {
    add(size(), e);
    return true;
  }

  @Override
  public boolean remove(Object o) {
    Predicate<Object> predicate = resolveEqualsPredicate(o);
    Iterator<E> iterator = iterator();
    while (iterator.hasNext()) {
      if (predicate.test(iterator.next())) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    Objects.requireNonNull(c);
    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    return addAll(size(), c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    Objects.requireNonNull(c);
    int oldSize = size();
    ListIterator<E> listIterator = listIterator(index);
    for (E e : c) {
      listIterator.add(e);
    }
    return oldSize != size();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    Objects.requireNonNull(c);
    int oldSize = size();
    removeIf(c::contains);
    return oldSize != size();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    Objects.requireNonNull(c);
    int oldSize = size();
    removeIf(e -> !c.contains(e));
    return oldSize != size();
  }

  @Override
  public void clear() {
    removeRange(0, size());
  }

  @Override
  public int indexOf(Object o) {
    return findIndexOf(resolveEqualsPredicate(o));
  }

  @Override
  public int lastIndexOf(Object o) {
    return findLastIndexOf(resolveEqualsPredicate(o));
  }

  @Override
  public ListIterator<E> listIterator() {
    return new ListIteratorImpl();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    ListUtils.checkIndexInclusiveLength(index, size());
    return new ListIteratorImpl(index);
  }

  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner(", ", "[", "]");
    for (E next : this) {
      joiner.add(Objects.isNull(next) ? "null" : next.toString());
    }
    return joiner.toString();
  }

  public void removeRange(int fromIndex, int toIndex) {
    ListIterator<E> listIterator = listIterator(fromIndex);
    int removeCount = toIndex - fromIndex;
    for (int i = 0; i < removeCount; i++) {
      listIterator.next();
      listIterator.remove();
    }
  }

  private int findIndexOf(Predicate<Object> predicate) {
    ListIterator<E> listIterator = listIterator();
    while (listIterator.hasNext()) {
      if (predicate.test(listIterator.next())) {
        return listIterator.previousIndex();
      }
    }
    return -1;
  }

  private int findLastIndexOf(Predicate<Object> predicate) {
    ListIterator<E> listIterator = listIterator(size());
    while (listIterator.hasPrevious()) {
      if (predicate.test(listIterator.previous())) {
        return listIterator.nextIndex();
      }
    }
    return -1;
  }

  private class IteratorImpl implements Iterator<E> {

    int index = 0;

    int lastIndex = -1;

    int expectedSize = size();

    @Override
    public boolean hasNext() {
      return index < size();
    }

    @Override
    public E next() {
      checkForConcurrentModification();
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      E data = get(index);
      lastIndex = index;
      index++;
      return data;
    }

    @Override
    public void remove() {
      checkForIllegalState();
      checkForConcurrentModification();
      AbstractList.this.remove(lastIndex);
      index--;
      lastIndex = -1;
      expectedSize--;
    }

    protected void checkForIllegalState() {
      if (lastIndex == -1) {
        throw new IllegalStateException();
      }
    }

    protected void checkForConcurrentModification() {
      if (expectedSize != size()) {
        throw new ConcurrentModificationException();
      }
    }
  }

  private class ListIteratorImpl extends IteratorImpl
      implements ListIterator<E> {

    public ListIteratorImpl() {
      this(0);
    }

    public ListIteratorImpl(int index) {
      this.index = index;
    }

    @Override
    public boolean hasPrevious() {
      return index != 0;
    }

    @Override
    public E previous() {
      checkForConcurrentModification();
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      }
      lastIndex = --index;
      return get(index);
    }

    @Override
    public int nextIndex() {
      return index;
    }

    @Override
    public int previousIndex() {
      return index - 1;
    }

    @Override
    public void set(E e) {
      checkForConcurrentModification();
      checkForIllegalState();
      AbstractList.this.set(lastIndex, e);
    }

    @Override
    public void add(E e) {
      checkForConcurrentModification();
      AbstractList.this.add(index++, e);
      lastIndex = -1;
      expectedSize++;
    }
  }
}
