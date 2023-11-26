<div style="text-align: center;"><b><h1>Collections Module</h1></b>

*This module includes collections that go beyond the standard Java framework.
Each collection is designed for optimal efficiency.*

</div>

***

<div style="text-align: center;"><h3>Implementations</h3></div>

+ [SwapOnRemoveArray](./src/main/java/collections/array/SwapOnRemoveArray.java)
    + [SwapOnRemoveLongArray](./src/main/java/collections/array/SwapOnRemoveLongArray.java)
    + [SwapOnRemoveIntArray](./src/main/java/collections/array/SwapOnRemoveIntArray.java)
    + [SwapOnRemoveShortArray](./src/main/java/collections/array/SwapOnRemoveShortArray.java)
    + [SwapOnRemoveByteArray](./src/main/java/collections/array/SwapOnRemoveByteArray.java)
    + [SwapOnRemoveCharArray](./src/main/java/collections/array/SwapOnRemoveCharArray.java)
    + [SwapOnRemoveFloatArray](./src/main/java/collections/array/SwapOnRemoveFloatArray.java)
    + [SwapOnRemoveDoubleArray](./src/main/java/collections/array/SwapOnRemoveDoubleArray.java)
        + The SwapOnRemoveArray class is a resizable array-based collection designed for efficient addition and removal
          of
          elements. It employs a unique approach to removal by swapping the target element with the last element in the
          array, enhancing removal performance. This collection dynamically adjusts its size, doubling the capacity when
          necessary, ensuring optimal space utilization.
+ [ResizingArray](./src/main/java/collections/array/ResizingArray.java)
    + [ResizingLongArray](./src/main/java/collections/array/ResizingLongArray.java)
    + [ResizingIntArray](./src/main/java/collections/array/ResizingIntArray.java)
    + [ResizingShortArray](./src/main/java/collections/array/ResizingShortArray.java)
    + [ResizingByteArray](./src/main/java/collections/array/ResizingByteArray.java)
    + [ResizingCharArray](./src/main/java/collections/array/ResizingCharArray.java)
    + [ResizingFloatArray](./src/main/java/collections/array/ResizingFloatArray.java)
    + [ResizingDoubleArray](./src/main/java/collections/array/ResizingDoubleArray.java)
        + ResizingArray is a generic class that represents a dynamic array with automatic resizing capabilities.
+ [Bits](./src/main/java/collections/bits/Bits.java)
    + [LongBits](./src/main/java/collections/bits/LongBits.java)
    + [IntBits](./src/main/java/collections/bits/IntBits.java)
    + [ShortBits](./src/main/java/collections/bits/ShortBits.java)
    + [ByteBits](./src/main/java/collections/bits/ByteBits.java)
        + These classes provide basic operations for setting, clearing, finding
          the next set or clear bit, and comparing with another instance.
+ [FastStack](/src/main/java/collections/stack/FastStack.java)
    + [LongFastStack](./src/main/java/collections/stack/LongFastStack.java)
    + [IntFastStack](./src/main/java/collections/stack/IntFastStack.java)
    + [ShortFastStack](./src/main/java/collections/stack/ShortFastStack.java)
    + [ByteFastStack](./src/main/java/collections/stack/ByteFastStack.java)
    + [CharFastStack](./src/main/java/collections/stack/CharFastStack.java)
    + [FloatFastStack](./src/main/java/collections/stack/FloatFastStack.java)
    + [DoubleFastStack](./src/main/java/collections/stack/DoubleFastStack.java)
        + A generic implementation of a dynamic stack using an array-based approach. It supports dynamic resizing when
          the
          stack reaches its capacity and includes standard stack operations such as push, pop, and peek. The class also
          offers utility methods to check if the stack is empty, get its size, and clear its contents. Overall, it
          offers a
          flexible and efficient solution for implementing a last-in, first-out (LIFO) data structure in Java.
+ [RecyclingStack](./src/main/java/collections/stack/RecyclingStack.java)
    + A specialized implementation of a stack in Java that introduces a node recycling mechanism for improved memory
      efficiency. It supports the reuse of allocated nodes instead of creating new ones, making it particularly useful
      in scenarios where there is a frequent push-pop cycle. The class allows the initial pool allocation of nodes, and
      it provides methods for pushing, popping, and peeking at the top of the stack. Additionally, it includes features
      for removing specific nodes, checking for the presence of data, clearing the stack, and providing an iterator for
      easy traversal. The recycling mechanism is evident in the way nodes are managed, optimizing memory usage during
      the stack operations.
+ [BinarySearchTree](./src/main/java/collections/tree/BinarySearchTree.java)
    + Represents a binary search tree data structure in Java, designed to store comparable elements in a hierarchical
      structure. This implementation facilitates efficient operations such as
      insertion, deletion, and searching within the tree. The tree maintains its integrity by organizing elements based
      on their natural ordering. The structure includes a nested static class Node to represent individual nodes in the
      tree, each containing data, a reference to its left child, and a reference to its right child. The
      BinarySearchTree class is generic, allowing it to work with various comparable data types.