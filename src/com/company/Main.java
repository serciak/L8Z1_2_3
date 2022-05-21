package com.company;

public class Main {

    public static void main(String[] args) {
	    BinarySearchTree<Integer> bst = new BinarySearchTree<>();


        bst.rbInsert(8);
        bst.rbInsert(6);
        bst.rbInsert(49);
        bst.rbInsert(55);
        bst.rbInsert(50);
        bst.rbInsert(47);
        bst.rbInsert(60);
        bst.rbInsert(20);
        bst.rbInsert(30);
        //bst.rbInsert(37);
        bst.rbInsert(43);
        //bst.rbInsert(35);
        bst.rbInsert(45);
        bst.rbInsert(88);

        System.out.println(bst);
    }
}
