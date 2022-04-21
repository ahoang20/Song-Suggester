import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class SongSuggester {
    public static Scanner scanner = new Scanner(System.in);

    static boolean askYesNo(String question) throws Exception {
        System.out.println(question + " [y/n]");

        char answer = scanner.nextLine().charAt(0);

        if (answer == 'y') {
            return true;
        } else if (answer == 'n') {
            return false;
        } else {
            throw new Exception("Test");
        }
    }

    static TreeNode readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("suggestions.txt"));

        TreeNode root = null;
        try {
            root = TreeNode.fromPreorder(reader);
        } finally {
            reader.close();
        }

        return root;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome! Let's help you find a song.");
        String direction = null;
        TreeNode currentParent = null;
        TreeNode currentNode = null;

        File f = new File("suggestions.txt");
        if (f.exists() && !f.isDirectory()) {
            currentNode = readFile();
        } else {
            currentNode = new TreeNode("Do you like classcial music?", new TreeNode("Are you a fan of Mozart?",
                    new TreeNode("Eine Kleine Nachtmusik"),
                    new TreeNode("\"3 Romances\" by Clara Schumann")), new TreeNode("\"Take Five\" by Dave Brubeck"));
        }

        TreeNode head = currentNode;

        outerLoop: while (true) {
            currentNode = head;
            while (currentNode != null) {
                if (currentNode.isLeaf()) {
                    System.out.println(currentNode.value);
                    if (askYesNo("Is this satisfactory?")) {
                        if (askYesNo("Do you want to play again?") == true) {
                            break;
                        } else {
                            if (askYesNo("Do you want to save the current tree?")) {
                                BufferedWriter writer = new BufferedWriter(new FileWriter("suggestions.txt"));
                                try {
                                    head.saveFile(writer);
                                } finally {
                                    writer.close();
                                }
                            }
                            scanner.close();
                            break outerLoop;
                        }
                    } else {
                        System.out.println("What do you prefer instead?");
                        String preferredAnswer = scanner.nextLine();
                        System.out.println("Tell me a question that distinguishes " + currentNode.value + " from "
                                + preferredAnswer);
                        String answer = scanner.nextLine();
                        TreeNode node = new TreeNode(answer, new TreeNode(preferredAnswer), currentNode);
                        if (direction == "right") {
                            currentParent.right = node;
                        } else {
                            currentParent.left = node;

                        }
                        currentNode = node;
                        if (askYesNo("Do you want to play again?") == true) {
                            break;
                        } else {
                            if (askYesNo("Do you want to save the current tree?")) {
                                BufferedWriter writer = new BufferedWriter(new FileWriter("suggestions.txt"));
                                try {
                                    head.saveFile(writer);
                                } finally {
                                    writer.close();
                                }
                            }
                            scanner.close();
                            break outerLoop;
                        }
                    }

                }

                currentParent = currentNode;

                if (askYesNo(currentNode.value) == true) {
                    currentNode = currentNode.getLeft();
                    direction = "left";
                } else {
                    currentNode = currentNode.getRight();
                    direction = "right";
                }
            }
        }
    }
}