### 超赞字符串的判定
对于一个字符串能不能成为超赞字符串，只要符合下面其中一个条件即可。
1. 所有字符都出现了偶数次。
2. 有1个字符出现了奇数次，其余字符出现偶数次。

### 状态压缩
1. 由于上述的特点，我们对于一个字符串不需要去数每个字符出现的具体次数，只需要去算每个字符的出现次数是奇数还是偶数。

2. 字符串只是包含数字，即每个字符都处于['0','9']，字符串只可能存在10种字符。

3. 由1，2可得，可以用一个整数status的二进制格式表示当前字符串中每种字符出现的奇偶性（0为偶数次，1为奇数次）。

### 异或运算得出状态值
 我们遍历字符串，给每一位字符都算出到从第一位到此字符之间的status。

 这时候需要用到异或运算符，异或的特点就是相同时候取0，不同时取1，符合我们奇偶变化的标准。

 我们用0表示偶数，用1表示奇数，两个奇或者两个偶相遇时得到偶数，一奇一偶得到奇数。

 假设第n位的是数字x，那就写需要把1左移x位，表示x有一个奇数。然后和status进行异或运算： status = statu

 举例，假设字符串s为“123321”：

 字符串s | \ | 1|2|3|3|2|1
---|---|---|---|---|---|---|---
status | 0|2|6|14|6|2|0

 array1       1   2   3   3   2   1
 status   0   2   6   14  6   2   0

 ### 状态值的比较以及意义

 * 假设第n位和m位的status值相同，就可以代表n到m之间的字符串里的所有数字的出现次数都是偶数，那n到m之间的字符串就是一个超赞字符串了。


* 对于另外一种类型的超赞字符串：有一个字符出现了奇数次，其余字符出现偶数次。我们可以假设它的状态值statusNew =  status xor (1 shl extendNum)，extendNum为1-9之间的某一个数。

### 前缀和

 为了方便存储和快速找到status值对应的数组index，我们以status值为下标，以数组index为内容建立数组。

 数组大小是1 shl 11。1 shl 11包括了所有可能出现的status值。

 当status值相同时，我们保留最左边的值就行了。（因为是从左往右遍历，保留最左的值可以保证得出的字符串长度最长）

### 总流程
算法思路归纳如下
1. 遍历字符串
2. 求出当前字符的status值，寻找有无出现过相同的status值，有则计算长度，没有则记录。
3. 对于每个status，都用1 shr (1~9) 取异或得到预估的状态值statusNew，寻找有无出现过相同的statusNew值，有则计算长度，此次不需要记录。因为只是设想值而已。


### 代码
```
    fun longestAwesome10(s: String): Int {
        val statusIndexArray = IntArray(1 shl 11) {
            -2
        }
        var status = 0
        //0代表最开始遍历数组前的状态，坐标是-1。设置为-1是方便后面的计算。
        statusIndexArray[0] = -1
        //存放长的最赞字符串长度
        var result = 0
        val length = s.length
        for (index in 0 until length) {
            //求出当前index的status
            status = status xor (1 shl (s[index] - '0'))
            //判断变化次数全部为偶数的超赞字符串
            if (statusIndexArray[status] != -2) {
                //之前存在相同的状态值,通过index - statusIndexArray[status]求两个字符之间的长度，和result相比较
                result = Math.max(result, index - statusIndexArray[status])
            } else {
                //否则就赋值
                statusIndexArray[status] = index
            }
            //判断一个字符变化次数为奇数的超赞字符串。通过给status分别加上1左移0-9的值，在查看statusIndexArray中是否存在新的status，
            //如果存在就代表这两个status之间有且只有一个字符变化了奇数次。
            for (extendNum in 0..9) {
                //构造出一种比status多一个数会变化奇数次的status，然后查下statusIndexArray中是否存在新的status
                val statusNew = status xor (1 shl extendNum)
                if (statusIndexArray[statusNew] != -2) {
                    //之前存在statusNew,通过index - statusIndexArray[statusNew]求两个字符之间的长度，和result相比较
                    result = Math.max(result, index - statusIndexArray[statusNew])
                }
            }
        }
        return result
    }
```
