import java.util.*;
public class Q2 {

    public static boolean compareData(List<Map<String, String>> original, List<Map<String, String>> migrated) {
        if (original.size() != migrated.size()) {
            System.out.println("Row count mismatch");
            return false;
        }

        Comparator<Map<String, String>> byId = Comparator.comparing(m -> m.get("id"));
        List<Map<String, String>> sortedOriginal = new ArrayList<>(original);
        List<Map<String, String>> sortedMigrated = new ArrayList<>(migrated);
        sortedOriginal.sort(byId);
        sortedMigrated.sort(byId);

        for (int i = 0; i < sortedOriginal.size(); i++) {
            Map<String, String> row1 = sortedOriginal.get(i);
            Map<String, String> row2 = sortedMigrated.get(i);
            if (!row1.equals(row2)) {
                System.out.println("Mismatch found at row:" + i);
                System.out.println("Original:" + row1);
                System.out.println("Migrated:" + row2);
                return false;
            }
        }

        return true;
    }


    public static void main(String[] args) {
        // Sample data (original/local database)
        List<Map<String, String>> originalData = List.of(
                Map.of("id", "1", "name", "Alice", "salary", "5000"),
                Map.of("id", "2", "name", "Bob", "salary", "6000"),
                Map.of("id", "3", "name", "Charlie", "salary", "7000")
        );

        // Sample data (migrated/AWS database)
        List<Map<String, String>> migratedData = List.of(
                Map.of("id", "1", "name", "Alice", "salary", "5000"),
                Map.of("id", "2", "name", "Bob", "salary", "6000"),
                Map.of("id", "3", "name", "Charlie", "salary", "7000")
        );

        boolean isSame = compareData(originalData, migratedData);
        System.out.println("Is migrated data same as original? " + isSame);
    }
}
