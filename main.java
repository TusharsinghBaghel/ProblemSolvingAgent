import search.*;
import search.puzzles.eightpuzzle.*;
import search.puzzles.tsp.*;

import javax.swing.*; // added for visualization
import java.awt.*;     // added for visualization
import java.awt.geom.Ellipse2D;

public class main {
    public static void main(String[] args) {
//        // 1) Eight Puzzle Demo (unchanged)
//        int[] startTiles = {8, 6, 7,
//                3, 4, 5,
//                2, 0, 1};
//
//        int[] goalTiles = {1, 2, 3,
//                4, 5, 6,
//                7, 8, 0};
//
//        State start = new State(startTiles);
//        State goal = new State(goalTiles);
//
//        Problem<State, Action> puzzleProblem = new EightPuzzleProblem(start, goal);
//
//        if (!((EightPuzzleProblem) puzzleProblem).isSolvable()) {
//            System.out.println("No solution possible for this initial 8-puzzle state. Skipping to TSP demo.\n");
//        } else {
//            SearchAlgorithm<State, Action> searchAlgorithm = new Bidirectional<>();
//            searchAlgorithm.search(puzzleProblem).ifPresentOrElse(
//                    node -> {
//                        System.out.println("8-Puzzle solution found with " + node.pathCost + " moves.");
//                        System.out.println("Moves: " + node.getSolution());
//                        System.out.println("Final state:\n" + node.state);
//                    },
//                    () -> System.out.println("No 8-puzzle solution found."));
//        }

        // 2) Travelling Salesman Problem Demo solved via A* + MST (Kruskal) heuristic
        // Distance matrix (symmetric) for 5-city instance
//        double[][] distances = {
//                {0, 2, 9, 10, 7},
//                {2, 0, 6, 4, 3},
//                {9, 6, 0, 8, 5},
//                {10, 4, 8, 0, 6},
//                {7, 3, 5, 6, 0}
//        };
//        int startCity = 0;


        double[][] distances = {
                {0, 12, 19, 23, 17, 31, 28, 14, 22, 35},
                {12, 0, 8, 15, 11, 27, 25, 18, 24, 29},
                {19, 8, 0, 13, 9, 22, 21, 16, 15, 26},
                {23, 15, 13, 0, 7, 18, 20, 19, 17, 24},
                {17, 11, 9, 7, 0, 16, 18, 15, 14, 21},
                {31, 27, 22, 18, 16, 0, 10, 26, 32, 12},
                {28, 25, 21, 20, 18, 10, 0, 24, 30, 14},
                {14, 18, 16, 19, 15, 26, 24, 0, 13, 27},
                {22, 24, 15, 17, 14, 32, 30, 13, 0, 25},
                {35, 29, 26, 24, 21, 12, 14, 27, 25, 0}
        };

        int startCity = 0;



//// 20-node TSP instance
//        double[][] distances = {
//                {0, 29, 20, 21, 16, 31,100, 12, 4, 31, 18, 23, 11, 15, 28, 6, 36, 25, 14, 9},
//                {29, 0, 15, 29, 28, 40, 72, 21,29, 41, 12, 32, 25, 13, 19,23, 44, 34, 25,27},
//                {20,15, 0, 15, 14, 25, 81, 9,23, 27, 13, 16, 20, 11, 17,13, 36, 32, 17,28},
//                {21,29,15, 0, 4, 12, 92,12,25, 13, 25, 19, 32, 22, 29,21, 29, 41, 12,27},
//                {16,28,14, 4, 0, 16, 94, 9,20, 16, 22, 23, 28, 18, 24,17, 28, 39, 15,26},
//                {31,40,25,12,16, 0, 95,24,36, 3, 37, 9, 40, 32, 41,30, 13, 53, 26,17},
//                {100,72,81,92,94,95, 0,90,101,99, 84, 86, 91, 73, 77,88, 69,120, 90,98},
//                {12,21, 9,12, 9,24,90, 0,15,25, 15, 17, 19, 14, 18,10, 30, 27, 8,20},
//                {4,29,23,25,20,36,101,15, 0,34, 21, 27, 13, 19, 32, 9, 38, 23,18,13},
//                {31,41,27,13,16, 3,99,25,34, 0, 35, 10, 39, 30, 40,29, 14, 52, 25,18},
//                {18,12,13,25,22,37,84,15,21,35, 0, 28, 23, 9, 11,20, 37, 29, 19,24},
//                {23,32,16,19,23, 9,86,17,27,10,28, 0, 31, 26, 33,23, 16, 44, 21,12},
//                {11,25,20,32,28,40,91,19,13,39,23,31, 0, 22, 35,15, 41, 20, 22,20},
//                {15,13,11,22,18,32,73,14,19,30, 9,26,22, 0, 12,18, 33, 32, 16,21},
//                {28,19,17,29,24,41,77,18,32,40,11,33,35,12, 0,25, 42, 37, 28,30},
//                {6,23,13,21,17,30,88,10, 9,29,20,23,15,18,25, 0, 35, 24, 16,11},
//                {36,44,36,29,28,13,69,30,38,14,37,16,41,33,42,35, 0, 52, 32,19},
//                {25,34,32,41,39,53,120,27,23,52,29,44,20,32,37,24,52, 0, 35,31},
//                {14,25,17,12,15,26,90, 8,18,25,19,21,22,16,28,16,32,35, 0,22},
//                {9,27,28,27,26,17,98,20,13,18,24,12,20,21,30,11,19,31,22, 0}
//        };
//
//        int startCity = 0;




        TSPProblem tspProblem = new TSPProblem(distances, startCity);
        AStar.Heuristic<TSPState, TSPAction> mstHeuristic = new MSTHeuristic();
        SearchAlgorithm<TSPState, TSPAction> tspSearch = new AStar<>(mstHeuristic);

        System.out.println("\nRunning A* on TSP with MST heuristic (Kruskal)...");
        long begin = System.currentTimeMillis();
        tspSearch.search(tspProblem).ifPresentOrElse(
                node -> {
                    long elapsed = System.currentTimeMillis() - begin;
                    System.out.println("TSP tour cost: " + node.pathCost);
                    System.out.println("Action sequence: " + node.getSolution());
                    System.out.println("Goal state reached: " + node.state);
                    System.out.println("Elapsed ms: " + elapsed);
                },
                () -> System.out.println("No TSP solution found."));
        // Graph visualization (actual drawn graph instead of matrices/MST)
        visualizeGraph(distances);
    }

    // --- New visualization using Swing ---
    private static void visualizeGraph(double[][] d) {
        try {
            EventQueue.invokeLater(() -> {
                JFrame frame = new JFrame("TSP Graph");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(640, 640);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());
                frame.add(new GraphPanel(d), BorderLayout.CENTER);
                frame.setVisible(true);
            });
            System.out.println("Graph window opened (close it to end the program if it blocks).\n");
        } catch (HeadlessException e) {
            System.out.println("Headless environment: cannot open GUI. (No graph window).\n");
        }
    }

    // Panel that draws complete weighted graph in circular layout
    private static class GraphPanel extends JPanel {
        private final double[][] d;
        private final int n;
        private final int RADIUS = 230; // circle radius for node placement
        private final int NODE_SIZE = 34;

        GraphPanel(double[][] d) {
            this.d = d;
            this.n = d.length;
            setBackground(Color.WHITE);
            setToolTipText("Complete graph: edge labels are distances");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            int cx = w / 2;
            int cy = h / 2;

            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n - Math.PI / 2; // start at top
                int x = cx + (int) (RADIUS * Math.cos(angle));
                int y = cy + (int) (RADIUS * Math.sin(angle));
                pts[i] = new Point(x, y);
            }

            // Draw edges first
            g2.setStroke(new BasicStroke(1.2f));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    Point a = pts[i];
                    Point b = pts[j];
                    g2.setColor(new Color(60, 60, 60, 180));
                    g2.drawLine(a.x, a.y, b.x, b.y);
                    // Edge weight label midpoint
                    int mx = (a.x + b.x) / 2;
                    int my = (a.y + b.y) / 2;
                    g2.setColor(new Color(10, 10, 10));
                    String label = String.valueOf((int) d[i][j]);
                    g2.drawString(label, mx + 4, my - 4);
                }
            }

            // Draw nodes on top
            for (int i = 0; i < n; i++) {
                Point p = pts[i];
                int x = p.x - NODE_SIZE / 2;
                int y = p.y - NODE_SIZE / 2;
                Shape circle = new Ellipse2D.Double(x, y, NODE_SIZE, NODE_SIZE);
                g2.setColor(i == 0 ? new Color(0, 102, 204) : new Color(30, 144, 255));
                g2.fill(circle);
                g2.setColor(Color.DARK_GRAY);
                g2.setStroke(new BasicStroke(2f));
                g2.draw(circle);
                g2.setColor(Color.WHITE);
                String text = "C" + i;
                FontMetrics fm = g2.getFontMetrics();
                int tx = p.x - fm.stringWidth(text) / 2;
                int ty = p.y + fm.getAscent() / 2 - 3;
                g2.drawString(text, tx, ty);
            }

            // Legend
            g2.setColor(Color.BLACK);
            g2.drawString("Start node: C0 (blue)", 10, 20);
            g2.drawString("Complete graph with weights", 10, 36);
            g2.dispose();
        }
    }
}
