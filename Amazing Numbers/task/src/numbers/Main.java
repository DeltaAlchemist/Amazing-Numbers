package numbers;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWelcome to Amazing Numbers!");

        printInstructions();

        while (true) {
            System.out.print("\nEnter a request: ");
            String x = sc.nextLine();

            if (x.length() == 0) {
                printInstructions();
                continue;
            }

            Long n, m = 0L;
            boolean ism = false;
            Vector<String> props = null;
            HashSet<Properties> properties = new HashSet<>();

            try {
                String[] ss = x.split(" ");
                n = Long.parseLong(ss[0]);
                if (ss.length > 1) {
                    ism = true;
                    m = Long.parseLong(ss[1]);
                }
                if (ss.length > 2) {
                    props = new Vector<>(Arrays.asList(ss).subList(2, ss.length));
                }
            } catch (NumberFormatException e) {
                System.out.print("\nThe first parameter should be a natural number or zero.\n");
                continue;
            }


            if (n < 0) {
                System.out.println("\nThe first parameter should be a natural number or zero.");
                continue;
            }

            if (n == 0) {
                System.out.println("\nGoodbye!");
                break;
            }

            if (ism && m <= 0) {
                System.out.println("\nThe second parameter should be a natural number.");
                continue;
            }

            if (props != null) {
                HashSet<String> hashSet = new HashSet<>(Arrays.asList("EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC",
                        "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING"));

                for (int i = 0; i < props.size(); ++i) {
                    props.setElementAt(props.elementAt(i).toUpperCase(Locale.ROOT), i);
                }

                Vector<String> wrong = new Vector<>();

                for (int i = 0; i < props.size(); ++i) {
                    if (!hashSet.contains(props.elementAt(i))) {
                        wrong.add(props.elementAt(i));
                    } else {
                        properties.add(Properties.valueOf(props.elementAt(i)));
                    }
                }

                if (wrong.size() > 0) {
                    if (wrong.size() == 1) {
                        System.out.printf("The property [%sc] is wrong.)", wrong.elementAt(0));
                    } else {
                        System.out.print("The properties [");
                        for (int i = 0; i < wrong.size() - 1; ++i) {
                            System.out.print(wrong.elementAt(i) + ", ");
                        }
                        System.out.print(wrong.elementAt(wrong.size() - 1) + "] are wrong\n");
                    }
                    System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, " +
                            "SQUARE, SUNNY, JUMPING]\n");
                    continue;
                }

                if (properties.contains(Properties.ODD) && properties.contains(Properties.EVEN)) {
                    System.out.print("\nThe request contains mutually exclusive properties: [ODD, EVEN]\n" +
                            "There are no numbers with these properties.\n");
                    continue;
                }
                if (properties.contains(Properties.SQUARE) && properties.contains(Properties.SUNNY)) {
                    System.out.print("\nThe request contains mutually exclusive properties: [SQUARE, SUNNY]\n" +
                            "There are no numbers with these properties.\n");
                    continue;
                }
                if (properties.contains(Properties.DUCK) && properties.contains(Properties.SPY)) {
                    System.out.print("\nThe request contains mutually exclusive properties: [DUCK, SPY]\n" +
                            "There are no numbers with these properties.\n");
                    continue;
                }
            }


            if (!ism) {
                printProps(n);
            } else {
                if (props == null) {
                    for (long i = n; i < n + m; ++i) {
                        System.out.println(getProperties(i));
                    }
                } else {
                    System.out.println();
                    System.out.print(getProperties(n, m, properties));
                }
            }
        }
    }

    enum Properties {
        BUZZ {
            public boolean check(long num) {
                return num % 7 == 0 || num % 10 == 7;
            }
        },
        DUCK {
            public boolean check(long num) {
                String numStr = Long.toString(num);
                boolean duck = false;
                for (int i = 1; i < numStr.length(); i++) {
                    if (numStr.charAt(i) == '0') {
                        duck = true;
                        break;
                    }
                }
                return duck;
            }
        },
        PALINDROMIC {
            public boolean check(long num) {
                long temp;
                temp = num;
                long d;
                long reverse;
                reverse = 0;
                while (temp > 0) {
                    d = temp % 10;
                    reverse = reverse * 10 + d;
                    temp = temp / 10;
                }
                return reverse == num;
            }
        },
        GAPFUL {
            public boolean check(long num) {
                String numStr = Long.toString(num);
                String s1 = String.valueOf(numStr.charAt(0));
                String s2 = String.valueOf(numStr.charAt(numStr.length() - 1));
                String s = s1 + s2;
                int c = Integer.parseInt(s);
                return num >= 100 && num % c == 0;
            }
        },
        SPY {
            public boolean check(long num) {
                String numStr = Long.toString(num);
                int sum = 0;
                int product = 1;
                for (int i = 0; i < numStr.length(); i++) {
                    sum += Character.getNumericValue(numStr.charAt(i));
                    product *= Character.getNumericValue(numStr.charAt(i));
                }
                return (sum == product);
            }
        },
        SQUARE {
            public boolean check(long num) {
                double sqrt = Math.sqrt(num);
                return ((sqrt - Math.floor(sqrt)) == 0);
            }
        },
        SUNNY {
            public boolean check(long num) {
                double sqrt = Math.sqrt(num + 1);
                return ((sqrt - Math.floor(sqrt)) == 0);
            }
        },
        JUMPING {
            public boolean check(long num) {
                String n = String.valueOf(num);
                for (int i = 0; i < n.length() - 1; ++i) {
                    if (Math.abs(n.charAt(i) - n.charAt(i + 1)) != 1) {
                        return false;
                    }
                }
                return true;
            }
        },
        EVEN {
            public boolean check(long num) {
                return num % 2 == 0;
            }
        },
        ODD {
            public boolean check(long num) {
                return num % 2 != 0;
            }
        };

        public abstract boolean check(long num);

    }

    public static void printProps(long num) {
        System.out.print("\nProperties of ");
        System.out.println(num);
        for (var prop : Properties.values()) {
            System.out.printf("%12s: %s%n", prop.name().toLowerCase(), prop.check(num));
        }
    }

    public static String getProperties(long num) {
        StringBuilder res = new StringBuilder();
        res.append(num).append(" is ");
        for (var prop : Properties.values()) {
            if (prop.check(num)) {
                res.append(prop.name().toLowerCase()).append(", ");
            }
        }
        res.deleteCharAt(res.length() - 2);
        return res.toString();
    }

    public static String getProperties(long num, long m, HashSet<Properties> p) {
        StringBuilder res = new StringBuilder();
        int n = 0;
        while (n < m) {
            boolean correct = true;
            for (var s : p) {
                if (!s.check(num)) correct = false;
            }
            if (correct) {
                res.append(getProperties(num));
                res.append("\n");
                n++;
            }
            num++;
        }
        return res.toString();
    }

    public static void printInstructions() {
        System.out.println();
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
    }

}//class
