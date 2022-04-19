package com.pantifik.ds.list.linkedlist;

import static com.pantifik.ds.list.utils.ListUtils.resolveEqualsPredicate;
import com.pantifik.ds.list.AbstractList;
import com.pantifik.ds.list.utils.ListUtils;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Predicate;

public class DoublyLinkedList<E> extends AbstractList<E>
    implements Queue<E>, Deque<E> {

  private int size;
  private Node<E> head;
  private Node<E> tail;


  public DoublyLinkedList() {
    size = 0;
    head = tail = null;
  }

  public DoublyLinkedList(Collection<E> iterable) {
    Objects.requireNonNull(iterable);
    addAll(iterable);
  }

  @Override
  public Iterator<E> iterator() {
    return new LinkedIterator();
  }

  @Override
  public boolean add(E e) {
    if (isEmpty()) {
      addTheOnly(e);
    } else {
      addLastElement(e);
    }
    return true;
  }

  @Override
  public ListIterator<E> listIterator() {
    return new LinkedListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    ListUtils.checkIndexInclusiveLength(index, size);
    return new LinkedListIterator(index);
  }

  @Override
  public void addFirst(E e) {
    offerFirst(e);
  }

  @Override
  public void addLast(E e) {
    add(e);
  }

  @Override
  public boolean offerFirst(E e) {
    if (isEmpty()) {
      addTheOnly(e);
    } else {
      addFirstElement(e);
    }
    return true;
  }

  @Override
  public boolean offerLast(E e) {
    return add(e);
  }

  @Override
  public E removeFirst() {
    return remove();
  }

  @Override
  public E removeLast() {
    throwNoSuchElementIfEmpty();
    return removeLastElement();
  }

  @Override
  public E pollFirst() {
    return poll();
  }

  @Override
  public E pollLast() {
    if (isEmpty()) {
      return null;
    } else {
      return removeLastElement();
    }
  }

  @Override
  public E getFirst() {
    return element();
  }

  @Override
  public E getLast() {
    throwNoSuchElementIfEmpty();
    return getLastElement();
  }

  @Override
  public E peekFirst() {
    return peek();
  }

  @Override
  public E peekLast() {
    if (isEmpty()) {
      return null;
    } else {
      return getLast();
    }
  }

  @Override
  public boolean removeFirstOccurrence(Object o) {
    return remove(o);
  }

  @Override
  public boolean removeLastOccurrence(Object o) {
    Predicate<Object> predicate = resolveEqualsPredicate(o);
    Iterator<E> iterator = descendingIterator();
    while (iterator.hasNext()) {
      if (predicate.test(iterator.next())) {
        iterator.remove();
        return true;
      }
    }
    return false;
  }

  @Override
  public void push(E e) {
    addFirst(e);
  }

  @Override
  public E pop() {
    return removeFirst();
  }

  @Override
  public Iterator<E> descendingIterator() {
    return new DescendingLinkedIterator();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public E get(int index) {
    Objects.checkIndex(index, size);
    if (index == 0) {
      return getFirstElement();
    } else if (index == size) {
      return getLastElement();
    } else {
      return listIterator(index).next();
    }
  }

  @Override
  public E set(int index, E element) {
    Objects.checkIndex(index, size);
    if (index == 0) {
      return setHeadData(element);
    } else if (index == size - 1) {
      return setTailData(element);
    } else {
      ListIterator<E> listIterator = listIterator(index);
      E data = listIterator.next();
      listIterator.set(element);
      return data;
    }
  }

  @Override
  public void add(int index, E element) {
    ListUtils.checkIndexInclusiveLength(index, size);
    if (index == 0) {
      addFirstElement(element);
    } else if (index == size) {
      addLastElement(element);
    } else {
      ListIterator<E> listIterator = listIterator(index);
      listIterator.add(element);
    }
  }

  @Override
  public E remove(int index) {
    Objects.checkIndex(index, size);
    if (index == 0) {
      return removeFirstElement();
    } else if (index == size - 1) {
      return removeLastElement();
    } else {
      ListIterator<E> listIterator = listIterator(index);
      E data = listIterator.next();
      listIterator.remove();
      return data;
    }
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    Objects.checkFromToIndex(fromIndex, toIndex, size);
    throw new UnsupportedOperationException();
  }

  @Override
  public int hashCode() {
    int hashCode = 1;
    for (E e : this) {hashCode = 31 * hashCode + Objects.hashCode(e);}
    return hashCode;
  }

  public boolean equals(Object o) {
    if (o == this) {return true;}
    if (!(o instanceof DoublyLinkedList)) {return false;}

    ListIterator<E> e1 = listIterator();
    ListIterator<?> e2 = ((DoublyLinkedList<?>) o).listIterator();
    while (e1.hasNext() && e2.hasNext()) {
      E o1 = e1.next();
      Object o2 = e2.next();
      if (!(Objects.equals(o1, o2))) {return false;}
    }
    return !(e1.hasNext() || e2.hasNext());
  }

  @Override
  public boolean offer(E e) {
    return add(e);
  }

  @Override
  public E remove() {
    throwNoSuchElementIfEmpty();
    return removeFirstElement();
  }

  @Override
  public E poll() {
    if (isEmpty()) {
      return null;
    } else {
      return removeFirstElement();
    }
  }

  @Override
  public E element() {
    throwNoSuchElementIfEmpty();
    return getFirstElement();
  }

  @Override
  public E peek() {
    if (isEmpty()) {
      return null;
    } else {
      return getFirstElement();
    }
  }

  private void throwNoSuchElementIfEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
  }

  private E setHeadData(E element) {
    E data = head.data;
    head.data = element;
    return data;
  }

  private E setTailData(E element) {
    E data = tail.data;
    tail.data = element;
    return data;
  }

  private E getLastElement() {
    return tail.data;
  }

  private E getFirstElement() {
    return head.data;
  }

  private E removeFirstElement() {
    Node<E> node = head;
    E data = node.data;
    head = node.next;
    clearNode(node);
    size--;
    return data;
  }

  private E removeLastElement() {
    Node<E> node = tail;
    E data = node.data;
    tail = node.prev;
    clearNode(node);
    size--;
    return data;
  }

  private void clearNode(Node<E> node) {
    node.data = null;
    node.prev = node.next = null;
  }

  private void addTheOnly(E e) {
    head = tail = new Node<>(e);
    size++;
  }

  private void addLastElement(E e) {
    Node<E> newTail = new Node<>(tail, e);
    tail.next = newTail;
    tail = newTail;
    size++;
  }

  private void addFirstElement(E e) {
    head = new Node<>(e, head);
    size++;
  }

  private static class Node<E> {
    private E data;
    private Node<E> prev;
    private Node<E> next;

    public Node(E data) {
      this(null, data, null);
    }

    public Node(Node<E> prev, E data) {
      this(prev, data, null);
    }

    public Node(E data, Node<E> next) {
      this(null, data, next);
    }

    public Node(Node<E> prev, E data, Node<E> next) {
      this.data = data;
      this.prev = prev;
      this.next = next;
    }
  }

  private class LinkedIterator implements Iterator<E> {

    int index;

    Node<E> current;

    Node<E> last;

    int expectedSize;

    public LinkedIterator() {
      index = 0;
      current = head;
      last = null;
      expectedSize = size;
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public E next() {
      checkForConcurrentModification();
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      E data = current.data;
      last = current;
      current = current.next;
      index++;
      return data;
    }

    @Override
    public void remove() {
      checkForConcurrentModification();
      checkHasLast();
      removeNode(last);
      last = null;
      index--;
      expectedSize--;
    }

    protected void checkHasLast() {
      if (last == null) {
        throw new IllegalStateException();
      }
    }

    protected void checkForConcurrentModification() {
      if (expectedSize != size) {
        throw new ConcurrentModificationException();
      }
    }

    private void removeNode(Node<E> node) {
      if (node == head) {
        removeFirstElement();
      } else if (node == tail) {
        removeLastElement();
      } else {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        clearNode(node);
        size--;
      }
    }
  }

  private class LinkedListIterator extends LinkedIterator
      implements ListIterator<E> {

    public LinkedListIterator() {
      super();
      index = 0;
    }

    public LinkedListIterator(int index) {
      super();
      advanceToIndex(index);
    }

    @Override
    public boolean hasPrevious() {
      if (Objects.isNull(current)) {
        return tail != null;
      } else {
        return current.prev != null;
      }
    }

    @Override
    public E previous() {
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      }
      checkForConcurrentModification();
      current = Objects.isNull(current) ? tail : current.prev;
      E data = current.data;
      last = current;
      index--;
      return data;
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
      checkHasLast();
      last.data = e;
    }

    @Override
    public void add(E e) {
      checkForConcurrentModification();
      addNode(current, e);
      last = null;
      index++;
      expectedSize++;
    }

    private void addNode(Node<E> node, E e) {
      if (isEmpty()) {
        addTheOnly(e);
      } else if (node == null) {
        addLastElement(e);
      } else if (node.prev == null) {
        addFirstElement(e);
      } else {
        Node<E> newNode = new Node<>(node.prev, e, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
      }
    }

    private void advanceToIndex(int index) {
      if (index > (size / 2)) {
        this.index = size;
        current = null;
        while (this.index > index) {
          previous();
        }
      } else {
        while (this.index < index) {
          next();
        }
      }
      last = null;
    }
  }

  private class DescendingLinkedIterator extends LinkedListIterator {

    public DescendingLinkedIterator() {
      super(size);
    }

    @Override
    public boolean hasNext() {
      return hasPrevious();
    }

    @Override
    public E next() {
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      }
      return previous();
    }

  }

}
