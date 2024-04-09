package demo;

public class Demo {

    public static void main(String[] args) throws Exception{
        String cn = "我爱中国";
        String en = "I love china";
        //
        byte[] cnBy = cn.getBytes("GBK");
        byte[] enBytes = en.getBytes();
        //
        System.out.println(cnBy.length);
        for(byte b : cnBy){
            System.out.printf("%d, ",b);
        }
        System.out.println("\n---------------------");
        System.out.println(enBytes.length);
        for(byte b : enBytes){
            System.out.printf("%d, ",b);
        }
        System.out.println();
        //
        byte[] temp = {-50,-46};
        String str = new String(temp,"UTF-8");
        System.out.println(str);
    }
}
