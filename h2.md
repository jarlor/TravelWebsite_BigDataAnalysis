# 数据分析

## 相关技术

### Hadoop概述

Hadoop是一个由Apache基金会所开发的分布式系统基础架构。用户可以在不了解分布式底层细节的情况下，开发分布式程序。充分利用集群的威力进行高速运算和存储。Hadoop实现了一个分布式文件系统（Distributed
File System），其中一个组件是HDFS（Hadoop Distributed File
System）。HDFS有高容错性的特点，并且设计用来部署在低廉的（low-cost）硬件上；而且它提供高吞吐量（high
throughput）来访问应用程序的数据，适合那些有着超大数据集（large data
set）的应用程序。HDFS放宽了（relax）POSIX的要求，可以以流的形式访问（streaming
access）文件系统中的数据。Hadoop的框架最核心的设计就是：HDFS和MapReduce。HDFS为海量的数据提供了存储，而MapReduce则为海量的数据提供了计算。

### 核心架构

Hadoop 由许多元素构成。其最底部是 Hadoop Distributed File
System（HDFS），它存储Hadoop集群中所有存储节点上的文件。HDFS的上一层是MapReduce引擎，该引擎由JobTrackers和TaskTrackers组成。通过对Hadoop分布式计算平台最核心的分布式文件系统HDFS、MapReduce处理过程，以及数据仓库工具Hive和分布式数据库Hbase的介绍，基本涵盖了Hadoop分布式平台的所有技术核心。

### HDFS

对外部客户机而言，HDFS就像一个传统的分级文件系统。可以创建、删除、移动或重命名文件，等等。但是HDFS的架构是基于一组特定的节点构建的，这是由它自身的特点决定的。这些节点包括NameNode（仅一个），它在HDFS内部提供元数据服务；DataNode，它为HDFS提供存储块。由于仅存在一个NameNode，因此这是HDFS
1.x版本的一个缺点（单点失败）。在Hadoop
2.x版本可以存在两个NameNode，解决了单节点故障问题。

存储在HDFS中的文件被分成块，然后将这些块复制到多个计算机中（DataNode）。这与传统的RAID架构大不相同。块的大小（1.x版本默认为64MB，2.x版本默认为128MB）和复制的块数量在创建文件时由客户机决定。

NameNode可以控制所有文件操作，HDFS内部的所有通信都基于标准的TCP/IP协议。

### NameNode

NameNode是一个通常在HDFS实例中的单独机器上运行的[软件](https://baike.baidu.com/item/%E8%BD%AF%E4%BB%B6)。它负责管理文件系统名称空间和控制外部客户机的访问。NameNode决定是否将文件映射到DataNode上的复制块上。对于最常见的3个复制块，第一个复制块存储在同一机架的不同节点上，最后一个复制块存储在不同机架的某个节点上。

实际的I/O[事务](https://baike.baidu.com/item/%E4%BA%8B%E5%8A%A1)并没有经过NameNode，只有表示DataNode和块的文件映射的元数据经过NameNode。当外部客户机发送请求要求创建文件时，NameNode会以块标识和该块的第一个副本的DataNodeIP地址作为响应。这个NameNode还会通知其他将要接收该块的副本的DataNode。

NameNode在一个称为FsImage的文件中存储所有关于文件系统[名称空间](https://baike.baidu.com/item/%E5%90%8D%E7%A7%B0%E7%A9%BA%E9%97%B4)的信息。这个文件和一个包含所有事务的[记录文件](https://baike.baidu.com/item/%E8%AE%B0%E5%BD%95%E6%96%87%E4%BB%B6)（这里是EditLog）将存储在NameNode的本地文件系统上。FsImage和EditLog文件也需要复制副本，以防文件损坏或NameNode系统丢失。

NameNode本身不可避免地具有SPOF（Single Point Of
Failure）单点失效的风险，主备模式并不能解决这个问题，通过Hadoop Non-stop
Namenode才能实现100%uptime可用时间。

### DataNode

DataNode也是一个通常在[HDFS](https://baike.baidu.com/item/HDFS)实例中的单独机器上运行的软件。Hadoop集群包含一个NameNode和大量DataNode。DataNode通常以机架的形式组织，机架通过一个[交换机](https://baike.baidu.com/item/%E4%BA%A4%E6%8D%A2%E6%9C%BA)将所有系统连接起来。Hadoop的一个假设是：机架内部[节点](https://baike.baidu.com/item/%E8%8A%82%E7%82%B9)之间的传输速度快于机架间节点的传输速度。

DataNode响应来自HDFS客户机的读写请求。它们还响应来NameNode的创建、删除和复制块的命令。NameNode依赖来自每个DataNode的定期心跳（heartbeat）消息。每条消息都包含一个块报告，NameNode可以根据这个报告验证块映射和其他文件系统元数据。如果DataNode不能发送心跳消息，NameNode将采取修复措施，重新复制在该节点上丢失的块。

## 数据统计

mapreduce程序分成三个部分：Mapper、Reducer和Driver。

1.  Mapper阶段

(1) 用户自定义的Mapp er要继承自己的父类。

(2) Mapper的输入数据是\<K,V\>的形式(K、V的类型可自定义)。

(3) Mapper中的业务逻辑写在map()方法中。

(4) Mapper的输出数据是\<K,V\>的形式。

(5)map()方法(MapTask进程)对每一个\<K,V\>调用一次。

1.  Reducer阶段

(1) 用户自定义的Reducer要继承自己的父类

(2) Reducer的输入数据类型对应Mapper的输出数据类型，也是\<K,V\>。

(3) Reducer的业务逻辑写在reduce()方法中。

(4) ReduceTask进程对每一组相同K的\<K,V\>组调用一次reduce()方法。

1.  Driver阶段

(1) 相当于YARN集群的客户端，用于提交我们整个程序到YARN集群。

(2) 提交的是封装了MapReduce程序相关运行参数的job对象。

### 