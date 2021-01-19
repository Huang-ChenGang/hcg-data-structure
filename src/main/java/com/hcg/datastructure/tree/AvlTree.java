package com.hcg.datastructure.tree;

public class AvlTree<T extends Comparable<? super T>> {

    private static final int ALLOWED_IMBALANCE = 1;

    private AvlNode<T> root;

    public AvlTree() {
        this.root = null;
    }

    public boolean empty() {
        return this.root == null;
    }

    public void makeEmpty() {
        this.root = null;
    }

    public T findMin() {
        if (empty()) {
            return null;
        }
        return findMin(this.root).element;
    }

    public T findMax() {
        if (empty()) {
            return null;
        }
        return findMax(this.root).element;
    }

    public void insert(T element) {
        this.root = insert(element, this.root);
    }

    public void remove(T element) {
        this.root = remove(element, this.root);
    }

    public boolean contains(T element) {
        return contains(element, this.root);
    }

    public void printTree() {
        if (empty()) {
            System.out.println("Empty Tree");
        } else {
            printTree(this.root);
        }
    }

    private void printTree(AvlNode<T> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    private boolean contains(T element, AvlNode<T> t) {
        while (t != null) {
            int compareResult = element.compareTo(t.element);

            if (compareResult < 0) {
                t = t.left;
            } else if (compareResult > 0) {
                t = t.right;
            } else {
                return true;
            }
        }

        return false;
    }

    private AvlNode<T> findMin(AvlNode<T> t) {
        if (t == null) {
            return null;
        }

        while (t.left != null) {
            t = t.left;
        }

        return t;
    }

    private AvlNode<T> findMax(AvlNode<T> t) {
        if (t == null) {
            return null;
        }

        while (t.right != null) {
            t = t.right;
        }

        return t;
    }

    private AvlNode<T> insert(T element, AvlNode<T> t) {
        if (t == null) {
            return new AvlNode<>(element);
        }

        int compareResult = element.compareTo(t.element);

        if (compareResult < 0) {
            t.left = insert(element, t.left);
        } else if (compareResult > 0) {
            t.right = insert(element, t.right);
        }

        return balance(t);
    }

    private AvlNode<T> remove(T element, AvlNode<T> t) {
        if (t == null) {
            return null;
        }

        int compareResult = element.compareTo(t.element);

        if (compareResult < 0) {
            t.left = remove(element, t.left);
        } else if (compareResult > 0) {
            t.right = remove(element, t.right);
        } else if (t.left != null && t.right != null) {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }

        return balance(t);
    }

    private AvlNode<T> balance(AvlNode<T> t) {
        // 左子树高度破坏了平衡
        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                // 情景1:LL
                t = rotateWithLeftChild(t);
            } else {
                // 情景2:LL
                t = doubleRotateWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            // 右子树高度破坏了平衡
            if (height(t.right.right) >= height(t.right.left)) {
                // 情景4:RR
                t = rotateWithRightChild(t);
            } else {
                // 情景3:RL
                t = doubleRotateWithRightChild(t);
            }
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /**
     * 情景2:LR，左子树的右子树较高，破坏了平衡
     * 先以旧节点的左节点为旧根进行左旋，然后以总的根节点进行右旋
     * @param oldRoot 旧根
     * @return 新根
     */
    private AvlNode<T> doubleRotateWithLeftChild(AvlNode<T> oldRoot) {
        oldRoot.left = rotateWithRightChild(oldRoot.left);
        return rotateWithLeftChild(oldRoot);
    }

    /**
     * 情景3:RL，右子树的左子树较高，破坏了平衡
     * 先以旧节点的右节点为旧根进行右旋，然后以总的根节点进行左旋
     * @param oldRoot 旧根
     * @return 新根
     */
    private AvlNode<T> doubleRotateWithRightChild(AvlNode<T> oldRoot) {
        oldRoot.right = rotateWithLeftChild(oldRoot.right);
        return rotateWithRightChild(oldRoot);
    }

    /**
     * 情景1:LL，左子树的左子树较高，破坏了平衡
     * 右旋，左子树上提，作为新的根
     * @param oldRoot 旧根
     * @return 新根
     */
    private AvlNode<T> rotateWithLeftChild(AvlNode<T> oldRoot) {
        AvlNode<T> newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;
        oldRoot.height = Math.max(height(oldRoot.left), height(oldRoot.right)) + 1;
        newRoot.height = Math.max(height(newRoot.left), oldRoot.height) + 1;
        return newRoot;
    }

    /**
     * 情景4:RR，右子树的右子树较高，破坏了平衡
     * 左旋，右子树上提，作为新的根
     * @param oldRoot 旧根
     * @return 新根
     */
    private AvlNode<T> rotateWithRightChild(AvlNode<T> oldRoot) {
        AvlNode<T> newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;
        oldRoot.height = Math.max(height(oldRoot.left), height(oldRoot.right)) + 1;
        newRoot.height = Math.max(oldRoot.height, height(newRoot.right)) + 1;
        return newRoot;
    }

    private int height(AvlNode<T> node) {
        return node == null ? -1 : node.height;
    }

    private static class AvlNode<T> {
        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        int height;

        AvlNode(T element) {
            this(element, null, null);
        }

        AvlNode(T element, AvlNode<T> left, AvlNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }
}
