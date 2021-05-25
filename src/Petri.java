import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Petri {
    static int[] m;
    static int[][] o,j;
    static int p, t;
    static String[] types = {"Граничная", "Терминальная", "Дублирующая", "Внутренняя"};
    static TreeNode<int[]> root;
    static int inf = Integer.MAX_VALUE;

    public static void main(String[] args) {
        System.out.println("Введите количество позиций");
        Scanner in = new Scanner(System.in);
        p = in.nextInt();
        m = new int[p];
        System.out.println("Введите количество токенов для каждой позиции");
        for(int i = 0; i<p; i++){
            m[i] = in.nextInt();
        }
        System.out.println("Введите количество переходов");
        t = in.nextInt();
        in.nextLine();
        o = new int[t][p];
        j = new int[t][p];

        String l;
        System.out.println("Введите множества входных позиций для переходов 1 - " + t + " через запятую");
        for(int i = 0; i<t; i++){
            l=in.nextLine();
            int k=0;
            for(String str:l.split(",")){
                j[i][k]=Integer.parseInt(str);//Exception in this line
                k++;
            }
        }
        System.out.println("Введите множества выходных позиций для переходов 1 - " + t + " через запятую");
        for(int i = 0; i<t; i++){
            l=in.nextLine();
            int k=0;
            for(String str:l.split(",")){
                o[i][k]=Integer.parseInt(str);//Exception in this line
                k++;
            }
        }

         root = new TreeNode<>(m, types[0]);
        createTree(root);
        printTree(root);

    }

    public static boolean checkTransition(int[]j, int[]m){
        boolean isAllowed = true;
            for(int i = 0; i<p; i++){
                if(j[i]>m[i]){
                    isAllowed = false;
                }
            }
        return isAllowed;
    }

    public static void printTree(TreeNode<int[]> node){
        try(FileWriter fw = new FileWriter("tree.txt", false)){
            List<TreeNode<int[]>> list = node.children;
            for(int i = 0; i<list.size(); i++) {
                TreeNode<int[]> cur = list.get(i);
                fw.write(Arrays.toString(cur.data)+" , " +cur.type+" is children of "+Arrays.toString(node.data));
                // запись по символам
                fw.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createTree(TreeNode<int[]> node){
        if(checkTree(node.data)){
            node.type = types[2];
        } else {
            boolean isTerm = true;
            for (int i = 0; i < t; i++) {
                if (checkTransition(j[i], node.data)) {
                    isTerm = false;
                    node.type = types[3];
                    int[] newdata = new int[p];
                    for(int k = 0; k<p; k++){
                        if(node.data[k] != inf)
                            newdata[k] = node.data[k]+j[i][p]-o[i][p];
                        else newdata[k] = inf;
                    }
                    TreeNode<int[]> newnode = node.addChild(newdata,types[0]);
                    newnode.data = checkInf( newnode);
                    createTree(newnode);
                }
            }
            if(isTerm){
                node.type=types[1];
            }
        }
    }

    public static int[] checkInf(TreeNode<int[]> newnode){
        boolean isMore;
        TreeNode<int[]> parent = newnode.parent;
        TreeNode<int[]> res = newnode;
        while(parent != null){
            isMore = true;
            for(int i = 0; i<p; i++){
                if(newnode.data[i]<parent.data[i]){
                    isMore = false;
                }
            }
            if(isMore){
                for(int i = 0; i<p; i++){
                    if(newnode.data[i]>parent.data[i]){
                        res.data[i] = inf;
                    }
                }
            }
            parent = parent.parent;
        }
        return res.data;
    }

    private static boolean checkTree(int[] data) {
        for (TreeNode<int[]> node : root.children) {
            if(node.type != types[0]){
                if(node.data == data){
                    return true;
                }
            }
        }
        return false;
    }

}
