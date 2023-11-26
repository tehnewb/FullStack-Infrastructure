<div style="text-align: center;"><b><h1>Collections Module</h1></b>

*This module includes collections that go beyond the standard Java framework.
Each collection is designed for optimal efficiency.*

</div>

***

<div style="text-align: center;"><h3>Implementations</h3></div>

+ SwapOnRemoveArray
    + The SwapOnRemoveArray class is a resizable array-based collection designed for efficient addition and removal of
      elements. It employs a unique approach to removal by swapping the target element with the last element in the
      array, enhancing removal performance. This collection dynamically adjusts its size, doubling the capacity when
      necessary, ensuring optimal space utilization.
+ FastStack
    + A generic implementation of a dynamic stack using an array-based approach. It supports dynamic resizing when the
      stack reaches its capacity and includes standard stack operations such as push, pop, and peek. The class also
      offers utility methods to check if the stack is empty, get its size, and clear its contents. Overall, it offers a
      flexible and efficient solution for implementing a last-in, first-out (LIFO) data structure in Java.
+ RecyclingStack
    + A specialized implementation of a stack in Java that introduces a node recycling mechanism for improved memory
      efficiency. It supports the reuse of allocated nodes instead of creating new ones, making it particularly useful
      in scenarios where there is a frequent push-pop cycle. The class allows the initial pool allocation of nodes, and
      it provides methods for pushing, popping, and peeking at the top of the stack. Additionally, it includes features
      for removing specific nodes, checking for the presence of data, clearing the stack, and providing an iterator for
      easy traversal. The recycling mechanism is evident in the way nodes are managed, optimizing memory usage during
      the stack operations.
+ BinarySearchTree
    + Represents a binary search tree data structure in Java, designed to store comparable elements in a hierarchical structure. This implementation facilitates efficient operations such as
      insertion, deletion, and searching within the tree. The tree maintains its integrity by organizing elements based
      on their natural ordering. The structure includes a nested static class Node to represent individual nodes in the
      tree, each containing data, a reference to its left child, and a reference to its right child. The
      BinarySearchTree class is generic, allowing it to work with various comparable data types.