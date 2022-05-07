package test;

import test.test.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Codec {
    public String serialize(TreeNode root) {
        if (root == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        //层次非递归遍历(之所以用list不用Queue是因为Queue不能添加null)
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        while (queue.size() > 0) {
            TreeNode p = queue.remove(0);
            if (p == null) {
                sb.append("null,");
            } else {
                sb.append(p.val + ",");
                queue.add(p.left);
                queue.add(p.right);
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("null")) {
            return null;
        }

        String[] split = data.split(",");

        TreeNode root = new TreeNode(Integer.parseInt(split[0]));
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        int i = 1;
        while (queue.size() > 0) {
            TreeNode curRoot = queue.poll();
            TreeNode left = null;
            if (!split[i].equals("null")) {
                left = new TreeNode(Integer.parseInt(split[i]));
                queue.add(left);
            }
            i++;
            TreeNode right = null;
            if (!split[i].equals("null")) {
                right = new TreeNode(Integer.parseInt(split[i]));
                queue.add(right);
            }
            i++;
            curRoot.left = left;
            curRoot.right = right;
        }

        return root;
    }

    public static void main(String[] args) {
        Codec codec = new Codec();
        String s = "1,2,3,null,null,4,5,null,null,null,null,";
        TreeNode root = codec.deserialize(s);
        String serialize = codec.serialize(root);
        System.out.println();
    }
}
