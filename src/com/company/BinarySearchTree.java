package com.company;

import java.util.ArrayList;

public class BinarySearchTree<T extends Comparable<T>> {

    private class TreeNode<T> {
        T value;
        TreeNode<T> left;
        TreeNode<T> right;
        TreeNode<T> parent;
        int leftH;
        int rightH;
        int overload;
        int leftNodes;
        int rightNodes;
        char color;

        TreeNode(T value) {
            this.value = value;
            this.color = 'R';
        }
    }
    TreeNode<T> root;

//Zadanie 1 -------------------------
    private T min(TreeNode<T> node) {
        while (node.left != null)
            node = node.left;
        return node.value;
    }

    private T max(TreeNode<T> node) {
        while (node.right != null)
            node = node.right;
        return node.value;
    }

    public T max() {
        return max(root);
    }

    public T min() {
        return min(root);
    }

    private TreeNode<T> find(T value, TreeNode<T> node) {
        if(node.value == value)
            return node;

        int result = value.compareTo(node.value);
        if(result > 0)
            return find(value, node.right);
        return find(value, node.left);
    }

    public T parent(T value) {
        TreeNode<T> node = find(value, root);
        return node.parent.value;
    }

    public T successor(T value) {
        TreeNode<T> node = find(value, root);
        if(node.right != null) {
            return min(node.right);
        }
        TreeNode<T> temp = node.parent;
        while(temp != null && node == temp.right) {
            node = temp;
            temp = temp.parent;
        }
        if(temp != null)
            return temp.value;
        return null;
    }

    public String toStringPreOrder() {
        ArrayList<String> output = new ArrayList<>();
        preOrder(root, output);
        return String.join(", ", output);
    }

    private void preOrder(TreeNode<T> node, ArrayList<String> output) {
        if (node == null) {
            return;
        }

        output.add(node.value.toString());
        preOrder(node.left, output);
        preOrder(node.right, output);

    }

    public String toStringInOrder() {
        ArrayList<String> output = new ArrayList<>();
        inOrder(root, output);
        return String.join(", ", output);
    }

    private void inOrder(TreeNode<T> node, ArrayList<String> output) {
        if (node == null) {
            return;
        }

        inOrder(node.left, output);
        output.add(node.value.toString());
        inOrder(node.right, output);
    }

    public String toStringPostOrder() {
        ArrayList<String> output = new ArrayList<>();
        postOrder(root, output);
        return String.join(", ", output);
    }

    private void postOrder(TreeNode<T> node, ArrayList<String> output) {
        if (node == null) {
            return;
        }

        postOrder(node.left, output);
        postOrder(node.right, output);
        output.add(node.value.toString());

    }

//Zadanie 2 ------------------------
    public int heightLeft(T value) {
        TreeNode<T> node = find(value, root);
        return node.leftH;
    }

    public int heightRight(T value) {
        TreeNode<T> node = find(value, root);
        return node.rightH;
    }

    public int nodesLeft(T value) {
        TreeNode<T> node = find(value, root);
        return node.leftNodes;
    }

    public int nodesRight(T value) {
        TreeNode<T> node = find(value, root);
        return node.rightNodes;
    }

    public int overload(T value) {
        TreeNode<T> node = find(value, root);
        return node.overload;
    }

    private void updateHeightsLeft(TreeNode<T> node) {
        if(node != null) {
            node.leftH = height(node.left);
            node.rightH = height(node.right);
            node.overload = node.rightH - node.leftH;
            updateHeightsLeft(node.left);
        }
    }

    private void updateHeightsRight(TreeNode<T> node) {
        if(node != null) {
            node.leftH = height(node.left);
            node.rightH = height(node.right);
            node.overload = node.rightH - node.leftH;
            updateHeightsRight(node.right);
        }
    }

    public void insert(T value) throws DuplicateElementException {
        root = insert(root, value);
        updateHeightsLeft(root);
        updateHeightsRight(root);
    }

    private TreeNode<T> insert(TreeNode<T> node, T value) throws DuplicateElementException {
        if(node == null)
            return new TreeNode<>(value);
        int cmp = value.compareTo(node.value);
        if(cmp < 0) {
            node.leftNodes++;
            node.left = insert(node.left, value);
        }
        if(cmp > 0) {
            node.rightNodes++;
            node.right = insert(node.right, value);
        }
        if(cmp == 0)
            throw new DuplicateElementException();
        return node;
    }

    public boolean contains(T value) {
        return contains(value, root);
    }

    private boolean contains(T value, TreeNode<T> node) {
        if (node == null)
            return false;

        int result = value.compareTo(node.value);

        if (result < 0)
            return contains(value, node.left);

        if (result > 0)
            return contains(value, node.right);

        return true;
    }

    public void delete(T value) {
        delete(value, root);
    }

    private TreeNode<T> delete(T value, TreeNode<T> node) {
        if (node == null) {
            return null;
        }

        int result = value.compareTo(node.value);

        if (result < 0) {
            --node.leftNodes;
            node.left = delete(value, node.left);
        }
        else if (result > 0) {
            --node.rightNodes;
            node.right = delete(value, node.right);
        }
        else {
            if (node.right == null)
                return node.left;

            if (node.left == null)
                return node.right;

            node.value = min(node.right);
            --node.rightNodes;
            node.right = delete(node.value, node.right);
        }

        return node;
    }

//Zadanie 3 ------------------------------
    private int height(TreeNode<T> node) {
        if(node == null)
            return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    private StringBuilder indent(int level, StringBuilder sb) {
        for(int i = 0; i < Math.pow(2, level-1); i++)
            sb.append(" ");
        return sb;
    }

    private StringBuilder spacing(int level, StringBuilder sb) {
        for(int i = 0; i < (Math.pow(2, level-1)*2)-1; i++)
            sb.append(" ");
        return sb;
    }

    private void printLevel(ArrayList<TreeNode<T>> arr, int level, StringBuilder sb) {
        ArrayList<TreeNode<T>> nodes = new ArrayList<>();

        sb = indent(level, sb);

        for (TreeNode<T> node : arr) {

            sb.append(node == null?" ":node.value);

            spacing(level, sb);

            if(level>1){
                nodes.add(node == null? null:node.left);
                nodes.add(node == null? null:node.right);
            }
        }
        sb.append("\n");

        if(level>1){
            printLevel(nodes, level-1, sb);
        }
    }

    public String toString() {
        ArrayList<TreeNode<T>> arr = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        arr.add(root);
        printLevel(arr, height(root), sb);
        return sb.toString();
    }

//Zadanie 4 ------------------
    public void rbInsert(T value) {
        if(root == null) {
            root = new TreeNode<>(value);
            root.color = 'B';
        }
        else
            root = rbInsert(root, value);
    }

    private boolean ll = false;
    private boolean rr = false;
    private boolean lr = false;
    private boolean rl = false;
    private TreeNode<T> rbInsert(TreeNode<T> node, T value) {
        boolean conflict=false;

        if(node == null)
            return new TreeNode<T>(value);
        else if(value.compareTo(node.value) < 0)
        {
            node.left = rbInsert(node.left, value);
            node.left.parent = node;
            if(node!=root)
            {
                if(node.color=='R' && root.left.color=='R')
                    conflict = true;
            }
        }
        else
        {
            node.right = rbInsert(node.right,value);
            node.right.parent = node;
            if(node!=root)
            {
                if(node.color=='R' && node.right.color=='R')
                    conflict = true;
            }
        }

        if(this.ll)
        {
            node = rotateLeft(node);
            node.color = 'B';
            node.left.color = 'R';
            this.ll = false;
        }
        else if(this.rr)
        {
            node = rotateRight(node);
            node.color = 'B';
            node.right.color = 'R';
            this.rr  = false;
        }
        else if(this.rl)
        {
            node.right = rotateRight(node.right);
            node.right.parent = node;
            node = rotateLeft(node);
            node.color = 'B';
            node.left.color = 'R';

            this.rl = false;
        }
        else if(this.lr)
        {
            node.left = rotateLeft(node.left);
            node.left.parent = node;
            node = rotateRight(node);
            node.color = 'B';
            node.right.color = 'R';
            this.lr = false;
        }
        if(conflict) {
            if(node.parent.right == node) {
                if(node.parent.left==null || node.parent.left.color=='B') {
                    if(node.left!=null && node.left.color=='R')
                        this.rl = true;
                    else if(node.right!=null && node.right.color=='R')
                        this.ll = true;
                }
                else {
                    node.parent.left.color = 'B';
                    node.color = 'B';
                    if(node.parent!=root)
                        node.parent.color = 'R';
                }
            }
            else {
                if(node.parent.right==null || node.parent.right.color=='B') {
                    if(node.left!=null && node.left.color=='R')
                        this.rr = true;
                    else if(node.right!=null && node.right.color=='R')
                        this.lr = true;
                }
                else {
                    node.parent.right.color = 'B';
                    node.color = 'B';
                    if(node.parent!=root)
                        node.parent.color = 'R';
                }
            }
            conflict = false;
        }
        return node;
    }

    private TreeNode<T> rotateLeft(TreeNode<T> node) {
        TreeNode<T> x = node.right;
        TreeNode<T> y = x.left;
        x.left = node;
        node.right = y;
        node.parent = x;
        if(y != null)
            y.parent = node;
        return x;
    }

    private TreeNode<T> rotateRight(TreeNode<T> node) {
        TreeNode<T> x = node.left;
        TreeNode<T> y = x.right;
        x.right = node;
        node.left = y;
        node.parent = x;
        if(y != null)
            y.parent = node;
        return x;
    }
}
