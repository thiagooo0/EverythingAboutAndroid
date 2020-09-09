package com.kowksiuwang.everythingaboutandroid.map

/**
 * Map接口最基础的hash表实现类，这个实现类提供了map的所有操作，并且允许插入空的key和value。
 * hashmap差不多等同于hashtable，除了异步（unsynchronized）和允许空值。
 *
 * 如果hash算法能够把元素正确得分散到每个buckets中，hashmap对于最基本的操作(get,set)提供常数时间内的操作性能。
 * 遍历一遍所有元素所需要的时间，跟hashmap中设置的capacity和hashmap的实际size呈正相关。因此，如果很注重
 * hashmap的遍历性能的话，不要把capacity设置得太高。
 *
 * initial capacity（初始容量）和load factor（加载因子）对hashmap的性能有很大的影响。
 * capacity代表了hasmap中buckets的数量，initial capacity就是hasmap创建时候的capacity了。
 * load factor是用来测量hashmap是否满到需要自动扩容。当hashmap中元素的个数超过了load factor和当前capacity的
 * 乘积，hashmap就会去进行rehashed（内部的结构会重建），使hashmap拥有相当于现在两倍的buckets。
 *
 * 默认的load factor在时间损耗和空间损耗之间提供了一个折中的方案。更加高的值降低了空间的损耗，但是增加的查找时
 * 的性能损耗（反映在包括get，put方法在内的大多数hashmap操作上）。当设置initial capital的时候，应该考虑到
 * 预想中map的元素数量和load factor。如果initial capital比map中元素数量的最大值跟load factor的商还要大，那
 * rehash就永远不会发生。
 *
 * 如果有很多映射需要存储到hashmap中，那创建设置一个足够大的capacity比让hashmap自动扩容要高效。
 * 注意，如果传入hashmap中对象的key都用同一个hashcode（hashcode中获得），那会拖慢hashmap的性能。
 * 如果key们是Comparable的话，可能会使用key之间的比较排序来切断key之间的联系。
 *
 * hashmap不是同步的。如果多个线程同时访问hashmap，并且至少其中一个会修改map的结构，那就需要在外部加锁。（这里
 * 的修改map的结构包括了增加和删除操作。仅仅是一个已经存在的key对于的value的值，不算修改结构。）这个操作通常
 * 由封装了这个map的对象完成。
 * 如果没有这个对象存在，这个map就需要用Collections的synchronizedMap()方法包装起来。最好在创建的时候就完成，
 * 防止意味的异步访问。
 *  Map m = Collections.synchronizedMap(new HashMap(...));
 *
 *  这个类返回的iterator是由fail-fast机制的：map的iterator被创建后，除非是调用iterator自身的remove方法，任何
 *  结构上的改变都会导致iterator抛出ConcurrentModificationException异常。因此，面对并发修改，iterator会干净
 *  快速地报错，而不是冒着在未来某个时间点会出现不确定行为的风险苟活。
 *
 *
 * Created by GuoShaoHong on 9/9/2020.
 */
class MyHashMap<K, out V> : AbstractMap<K,V>(){
    override val entries: Set<Map.Entry<K, V>>
        get() = TODO("Not yet implemented")


}