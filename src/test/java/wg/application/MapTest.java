package wg.application;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wg.application.entity.Student;
import wg.application.util.LongArray;
import wg.application.util.MapUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:02 2022/5/7
 * @updateTime: 16:02 2022/5/7
 ************************************************************************/
@SpringBootTest
public class MapTest {

    @Test
    public void test1() {
        HashMap<String, Object> params = new HashMap<>();

        HashMap<String, Object> filterMap = new HashMap<>();

        filterMap.put("a", params.get("a")); // {a=null}

        System.out.println(filterMap);

        HashMap<String, Object> map = null;
        map.remove("123"); // 空指针异常

    }

    /**
     * 取 map 值最大的键
     */
    @Test
    public void test2() {
        Map<String, Integer> map = new HashMap();
        map.put("1", 8);
        map.put("2", 12);
        map.put("3", 53);
        map.put("4", 33);
        map.put("5", 11);
        map.put("6", 3);
        map.put("7", 3);

        List<Map.Entry<String, Integer>> list = new ArrayList(map.entrySet());
        Collections.sort(list, (o1, o2) -> (o2.getValue() - o1.getValue())); // 从大到小排

        String key = list.get(0).getKey();
        System.out.println(key);
    }

    @Test
    public void testExtend() {
        Map<String, Integer> map = new HashMap<String, Integer>(2);
        map.put("1", 8);
        map.put("2", 12);
        map.put("3", 53);
        map.put("4", 33);
        map.put("5", 11);
        map.put("6", 3);

        System.out.println(map);

        Integer integer = map.get("232");
        System.out.println(integer);
    }

    @Test
    public void testToMap() {
        ArrayList<Student> list = new ArrayList<>();

        Map<Integer, Student> studentMap = list.stream().collect(Collectors.toMap(Student::getId, m -> m));

        System.out.println(studentMap);
    }

    /**
     * https://github.com/lemire/javaewah
     * bitmap
     */
    @Test
    public void testBitmap() throws IOException {
        EWAHCompressedBitmap ewahBitmap1 = EWAHCompressedBitmap.bitmapOf(0, 2, 55, 64, 1 << 30);
        EWAHCompressedBitmap ewahBitmap2 = EWAHCompressedBitmap.bitmapOf(1, 3, 64, 1 << 30);
        System.out.println("bitmap 1: " + ewahBitmap1);
        System.out.println("bitmap 2: " + ewahBitmap2);

        // or
        EWAHCompressedBitmap orbitmap = ewahBitmap1.or(ewahBitmap2);
        System.out.println("bitmap 1 OR bitmap 2: " + orbitmap);
        System.out.println("memory usage: " + orbitmap.sizeInBytes() + " bytes");

        // and
        EWAHCompressedBitmap andbitmap = ewahBitmap1.and(ewahBitmap2);
        System.out.println("bitmap 1 AND bitmap 2: " + andbitmap);
        System.out.println("memory usage: " + andbitmap.sizeInBytes() + " bytes");

        // xor
        EWAHCompressedBitmap xorbitmap = ewahBitmap1.xor(ewahBitmap2);
        System.out.println("bitmap 1 XOR bitmap 2:" + xorbitmap);
        System.out.println("memory usage: " + xorbitmap.sizeInBytes() + " bytes");

        // fast aggregation over many bitmaps
        EWAHCompressedBitmap ewahBitmap3 = EWAHCompressedBitmap.bitmapOf(5, 55, 1 << 30);
        EWAHCompressedBitmap ewahBitmap4 = EWAHCompressedBitmap.bitmapOf(4, 66, 1 << 30);
        System.out.println("bitmap 3: " + ewahBitmap3);
        System.out.println("bitmap 4: " + ewahBitmap4);

        andbitmap = EWAHCompressedBitmap.and(ewahBitmap1, ewahBitmap2, ewahBitmap3, ewahBitmap4);
        System.out.println("b1 AND b2 AND b3 AND b4: " + andbitmap);

        // serialization
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Note: you could use a file output steam instead of ByteArrayOutputStream
        ewahBitmap1.serialize(new DataOutputStream(bos));
        EWAHCompressedBitmap ewahBitmap1new = new EWAHCompressedBitmap();
        byte[] bout = bos.toByteArray();
        ewahBitmap1new.deserialize(new DataInputStream(new ByteArrayInputStream(bout)));
        System.out.println("bitmap 1 (recovered) : " + ewahBitmap1new);
        if (!ewahBitmap1.equals(ewahBitmap1new)) throw new RuntimeException("Will not happen");
        //
        // we can use a ByteBuffer as backend for a bitmap
        // which allows memory-mapped bitmaps
        //
        ByteBuffer bb = ByteBuffer.wrap(bout);
        EWAHCompressedBitmap rmap = new EWAHCompressedBitmap(bb);
        System.out.println("bitmap 1 (mapped) : " + rmap);

        if (!rmap.equals(ewahBitmap1)) throw new RuntimeException("Will not happen");
        //
        // support for threshold function (new as of version 0.8.0):
        // mark as true a bit that occurs at least T times in the source
        // bitmaps
        //
        EWAHCompressedBitmap threshold2 = EWAHCompressedBitmap.threshold(2,
                ewahBitmap1, ewahBitmap2, ewahBitmap3, ewahBitmap4);
        System.out.println("threshold 2 : " + threshold2);
    }

    @Test
    public void testLongArray() {
        /**
         * 未指定大小时，long类型数组的大小默认为4，默认值为0
         */
        LongArray longArray = new LongArray();

        for (int position = 0; position < 4; position++) {
            long word = longArray.getWord(position);
            Assert.assertEquals(0L, word);
        }

        /**
         * 足以证明，long类型数组里的大小确实为4
         */
        try {
            longArray.getWord(4);
            Assert.fail("不应该到这里");
        } catch (Exception e) {
        }

        /**
         * setWord(...)方法不会改变“sizeInWords”属性的值，sizeInWords默认为1
         */
        longArray.setWord(0, 10L);
        int sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(1, sizeInWords);

        longArray.setWord(2, 12L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(1, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 0, 12, 0}
         * push_back后：{10, 9527, 12, 0}
         */
        longArray.push_back(9527L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(2, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 9527, 12, 0}
         * push_back后：{10, 9527, 9528, 0}
         */
        longArray.push_back(9528L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(3, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 9527, 9528, 0}
         * push_back后：{10, 9527, 9528, 9529, 0, 0, 0, 0}
         */
        longArray.push_back(9529L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(4, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 9527, 9528, 9529, 0, 0, 0, 0}
         * push_back后：{10, 9527, 9528, 9529, 9530, 0, 0, 0}
         */
        longArray.push_back(9530L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(5, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 9527, 9528, 9529, 9530, 0, 0, 0}
         * push_back后：{10, 9527, 9528, 9529, 9530, 9531, 0, 0}
         */
        longArray.push_back(9531L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(6, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 9527, 9528, 9529, 9530, 9531, 0, 0}
         * push_back后：{10, 9527, 9528, 9529, 9530, 9531, 9532, 0}
         */
        longArray.push_back(9532L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(7, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 9527, 9528, 9529, 9530, 9531, 9532, 0}
         * push_back后：{10, 9527, 9528, 9529, 9530, 9531, 9532, 9533, 0, 0, 0, 0, 0, 0, 0, 0}
         */
        longArray.push_back(9533L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(8, sizeInWords);

        /**
         * push_back(...)方法会改变“sizeInWords”属性的值，使其加1
         * push_back前：{10, 9527, 9528, 9529, 9530, 9531, 9532, 9533, 0, 0, 0, 0, 0, 0, 0, 0}
         * push_back后：{10, 9527, 9528, 9529, 9530, 9531, 9532, 9533, 9999999999, 0, 0, 0, 0, 0, 0, 0}
         */
        longArray.push_back(9999999999L);
        sizeInWords = longArray.sizeInWords();
        Assert.assertEquals(9, sizeInWords);
    }

    @Test
    public void testEWAHCompressedBitmap() {
        System.out.println("testing EWAH");
        long zero = 0;
        long specialval = 1l | (1l << 4) | (1l << 63);
        long notzero = ~zero;
        EWAHCompressedBitmap myarray1 = new EWAHCompressedBitmap();
        myarray1.addWord(zero);
        myarray1.addWord(zero);
        myarray1.addWord(zero);
        myarray1.addWord(specialval);
        myarray1.addWord(specialval);
        myarray1.addWord(notzero);
        myarray1.addWord(zero);
        Assert.assertEquals(myarray1.toList().size(), 6 + 64);
        EWAHCompressedBitmap myarray2 = new EWAHCompressedBitmap();
        myarray2.addWord(zero);
        myarray2.addWord(specialval);
        myarray2.addWord(specialval);
        myarray2.addWord(notzero);
        myarray2.addWord(zero);
        myarray2.addWord(zero);
        myarray2.addWord(zero);
        Assert.assertEquals(myarray2.toList().size(), 6 + 64);
        List<Integer> data1 = myarray1.toList();
        List<Integer> data2 = myarray2.toList();
        Vector<Integer> logicalor = new Vector<Integer>();
        {
            HashSet<Integer> tmp = new HashSet<Integer>();
            tmp.addAll(data1);
            tmp.addAll(data2);
            logicalor.addAll(tmp);
        }
        Collections.sort(logicalor);
        Vector<Integer> logicaland = new Vector<Integer>();
        logicaland.addAll(data1);
        logicaland.retainAll(data2);
        Collections.sort(logicaland);
        EWAHCompressedBitmap arrayand = myarray1.and(myarray2);
        Assert.assertTrue(arrayand.toList().equals(logicaland));
        EWAHCompressedBitmap arrayor = myarray1.or(myarray2);
        Assert.assertTrue(arrayor.toList().equals(logicalor));
        EWAHCompressedBitmap arrayandbis = myarray2.and(myarray1);
        Assert.assertTrue(arrayandbis.toList().equals(logicaland));
        EWAHCompressedBitmap arrayorbis = myarray2.or(myarray1);
        Assert.assertTrue(arrayorbis.toList().equals(logicalor));
        EWAHCompressedBitmap x = new EWAHCompressedBitmap();
        for (Integer i : myarray1.toList()) {
            x.set(i);
        }
        Assert.assertTrue(x.toList().equals(
                myarray1.toList()));
        x = new EWAHCompressedBitmap();
        for (Integer i : myarray2.toList()) {
            x.set(i);
        }
        Assert.assertTrue(x.toList().equals(myarray2.toList()));
        x = new EWAHCompressedBitmap();
        for (Iterator<Integer> k = myarray1.iterator(); k.hasNext(); ) {
            x.set(extracted(k));
        }
        Assert.assertTrue(x.toList().equals(myarray1.toList()));
        x = new EWAHCompressedBitmap();
        for (Iterator<Integer> k = myarray2.iterator(); k.hasNext(); ) {
            x.set(extracted(k));
        }
        Assert.assertTrue(x.toList().equals(myarray2.toList()));
    }

    int extracted(Iterator<Integer> k) {
        Integer next = k.next();
        return next;
    }

    @Test
    public void removeTest() {
        Map<String, Object> filterMap = new HashMap<>();

        final String companyName = "companyName";
        final String branchName = "branchName";
        final String regionName = "regionName";
        final String oilGasPoolName = "fieldName";

        filterMap.put(companyName, companyName);
        filterMap.put("123", 123);

        Set<String> keySet = new HashSet<>();
        keySet.add(companyName);
        keySet.add(branchName);
        keySet.add(regionName);
        keySet.add(oilGasPoolName);

        HashMap<String, Object> map = new HashMap<>(filterMap);

        if (MapUtil.removeSomeKey(filterMap, keySet).isEmpty()) {
            System.out.println("null");
        } else {
            filterMap.putAll(map);
        }

        System.out.println(filterMap);
    }

    @Test
    public void onlyKeySetTest() {
        Map<String, Object> filterMap = new HashMap<>();

        final String companyName = "companyName";
        final String branchName = "branchName";
        final String regionName = "regionName";
        final String oilGasPoolName = "fieldName";

        filterMap.put(companyName, companyName);
        filterMap.put("123", 123);

        Set<String> keySet = new HashSet<>();
        keySet.add(companyName);
        keySet.add(branchName);
        keySet.add(regionName);
        keySet.add(oilGasPoolName);

        boolean onlySomeKey = MapUtil.containsSomeKey(filterMap, keySet);
        System.out.println(onlySomeKey);

        System.out.println(filterMap);
    }

    @Test
    public void hasOtherKeyTest() {
        Map<String, Object> filterMap = new HashMap<>();

        final String companyName = "companyName";
        final String branchName = "branchName";
        final String regionName = "regionName";
        final String oilGasPoolName = "fieldName";

        filterMap.put(companyName, companyName);
        filterMap.put("123", 123);

        Set<String> keySet = new HashSet<>();
        keySet.add(companyName);
        keySet.add(branchName);
        keySet.add(regionName);
        keySet.add(oilGasPoolName);

        boolean b = MapUtil.hasOtherKey(filterMap, keySet);

        if (b) {
            System.out.println("has other key");
        }

        // filterMap.containsKey(keySet); // 并不能 判断 key 集合

        if (MapUtil.containsKey(filterMap, keySet) && MapUtil.hasOtherKey(filterMap, keySet)) {
            System.out.println("不仅有 keyset 里的key, 还有 之外的 key");
        }

        System.out.println(filterMap);
    }

    /************************************************************************
     * @author: wg
     * @description: 融合两个map
     * @params:
     * @return:
     * @createTime: 15:02  2023/1/13
     * @updateTime: 15:02  2023/1/13
     ************************************************************************/
    @Test
    public void mergeMap() {
        Map<Integer, Integer> map1 = new HashMap<>();
        System.out.println(map1.get("get")); // null
        map1.put(1, 1);
        map1.put(2, 2);
        map1.put(3, 3);
        map1.put(4, 4);

        Map<Integer, Integer> map2 = new HashMap<>();
        map2.put(1, 0);
        map2.put(5, 5);
        map2.put(6, 6);
        map2.put(3, 4);

        //将map1和map2收集成一个流
        Stream<Map.Entry<Integer, Integer>> concat = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream());

        //然后将其收集成一个新的map
        Map<Integer, Integer> map = concat.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
        map.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });

        System.out.println();

        // 新map 谁 覆盖谁 的问题, 看代码
        Stream<Map.Entry<Integer, Integer>> concat2 = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream());
        Map<Integer, Integer> map22 = concat2.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
        map22.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
    }

    /************************************************************************
     * @author: wg
     * @description: 判断 key 是否存在 或 是否为 null, 如果为 null , 则 put key
     * @params:
     * @return:
     * @createTime: 10:08  2023/9/4
     * @updateTime: 10:08  2023/9/4
     ************************************************************************/
    @Test
    public void testAbsent() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("wg", null);

        System.out.println("map.get(\"wg\") = " + map.get("wg")); // map.get("wg") = null

        map.putIfAbsent("wg", 22D);
        System.out.println();
        System.out.println("map.get(\"wg\") = " + map.get("wg")); // map.get("wg") = 22.0

        // ****************************************************************************************************************************** //

        // 创建一个 HashMap
        HashMap<String, Integer> prices = new HashMap<>();

        // 往HashMap中添加映射项
        prices.put("Shoes", 200);
        prices.put("Bag", 300);
        prices.put("Pant", 150);
        prices.put("Shirt", 200);
        System.out.println("HashMap: " + prices); // HashMap: {Pant=150, Shirt=200, Bag=300, Shoes=200}

        // 计算 Shirt 的值
        int shirtPrice = prices.computeIfAbsent("Shirt", key -> 500);
        System.out.println("Price of Shirt: " + shirtPrice); // Price of Shirt: 200

        // 输出更新后的HashMap
        System.out.println("Updated HashMap: " + prices); // Updated HashMap: {Pant=150, Shirt=200, Bag=300, Shoes=200}

        Integer shirt = prices.compute("Shirt", (k, v) -> {
            System.out.println(k + "--" + v); // Shirt--200
            int i = v + 1;
            return i;
        });
        System.out.println("prices = " + prices); // prices = {Pant=150, Shirt=201, Bag=300, Shoes=200}
    }
}
