package wg.application.map;

/************************************************************************
 * @author: wg
 * @description:
 * redis 里 bitmap 应用:
 * 很多App都有一个签到功能，比如说连续签到7天或者30天给一些奖励，需求十分简单！
 *
 * 作为后端，我们需要提供一个签到接口，然后记录用户签到的信息，比如用户uid，签到时间！
 *
 * 如果使用传统关系型数据库，我们可能需要建一张签到表，大概有id、uid、createdTime等几个字段，当用户签到的时候新增一条记录就行！这种做法肯定
 * 是没问题的，但是如果网站每天有千万用户签到，那么这张表每天都会有千万条记录产生，数据的存储是问题！分库分表必不可少！
 *
 * 假如使用redis的bit操作，我们可以使用setbit，SETBIT key offset value 对指定的key的value的指定偏移(offset)的位置1或0, 其中key我们
 * 可以设置为当天的年月日，offset是用户uid（这里暂时只考虑uid是纯数字的情况）,value的话1表示已签到。比如说用户uid位12500的用户在20190501
 * 签到了，我们可以执行SETBIT 20190501 12500 1,其它用户依此论推！
 *
 * 如果我们需要查询用户某天是否签到，只需要使用 GETBIT 20190501 12500，返回1表示已签到，0未签到。
 *
 * 如果需要查询某天有多少人签到，可以使用BITCOUNT 20190501。
 *
 * 如果要统计连续7天签到的总人数的话可以使用bitop命令，比如bitop AND 7_dasy_sign 20190501 20190502 20190503 ... 20190507。
 * @params:
 * @return:
 * @createTime: 17:18  2022/6/30
 * @updateTime: 17:18  2022/6/30
 ************************************************************************/
public class BitMapTest {

    /**
     * 创建bitmap数组
     */
    public byte[] create(int n){
        byte[] bits = new byte[getIndex(n) + 1];
        
        for(int i = 0; i < n; i++){
            add(bits, i);
        }
        
        System.out.println(contains(bits, 11));
        
        int index = 1;
        for(byte bit : bits){
            System.out.println("-------" + index++ + "-------");
            showByte(bit);

        }
        
        return bits;
    }
    
    /**
     * 标记指定数字（num）在bitmap中的值，标记其已经出现过<br/>
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做|，这样，那个位置就替换成1了
     *
     *
     */
    public void add(byte[] bits, int num){
        bits[getIndex(num)] |= 1 << getPosition(num);
    }
    
    /**
     * 判断指定数字num是否存在<br/>
     * 将1左移position后，那个位置自然就是1，然后和以前的数据做&，判断是否为0即可
     * @param bits
     * @param num
     * @return
     */
    public boolean contains(byte[] bits, int num){
        return (bits[getIndex(num)] & 1 << getPosition(num)) != 0;
    }
    
    /**
     * num/8得到byte[]的index
     * @param num
     * @return
     */
    public int getIndex(int num){
        return num >> 3;
    }
    
    /**
     * num%8得到在byte[index]的位置
     * num & 111
     */
    public int getPosition(int num){
        return num & 0x07;
    }
    
    /**
     * 重置某一数字对应在bitmap中的值<br/>
     * 对1进行左移，然后取反，最后与byte[index]作与操作。
     * @param bits
     * @param num
     */
    public void clear(byte[] bits, int num){
        bits[getIndex(num)] &= ~(1 << getPosition(num));
    }
    
    /**
     * 打印byte类型的变量<br/>
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    
    public void showByte(byte b){
        byte[] array = new byte[8];
        for(int i = 7; i >= 0; i--){
            array[i] = (byte)(b & 1);
            b = (byte)(b >> 1);
        }
        
        for (byte b1 : array) {
            System.out.print(b1);
            System.out.print(" ");
        }
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        int n = 100;
        new BitMapTest().create(n);
    }

    // public static void main(String[] args) {
    //     int n = 100;
    //     BitMapTest bitMapTest = new BitMapTest();
    //     int index = bitMapTest.getIndex(n);
    //     int position = bitMapTest.getPosition(n);
    //     byte[] bits = new byte[index + 1];
    // }
}