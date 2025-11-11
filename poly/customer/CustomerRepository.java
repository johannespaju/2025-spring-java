package poly.customer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {

    private static final String FILE_PATH = "src/poly/customer/data.txt";

    private List<AbstractCustomer> customers = new ArrayList<>();

    public CustomerRepository() {
        loadCustomersFromFile();
    }

    public Optional<AbstractCustomer> getCustomerById(String id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public void remove(String id) {
        customers.removeIf(customer -> customer.getId().equals(id));
        saveCustomersToFile();
    }

    public void save(AbstractCustomer customer) {
        // Eemalda olemasolev klient sama ID-ga (update)
        customers.removeIf(c -> c.getId().equals(customer.getId()));

        // Lisa uus/uuendatud klient
        customers.add(customer);

        saveCustomersToFile();
    }

    public int getCustomerCount() {
        return customers.size();
    }

    public List<AbstractCustomer> getAllPaged(int pageNumber, int pageSize) {
        return List.of(); // only needed for icd0019app project
    }

    private void loadCustomersFromFile() {
        customers.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            while (line != null) {
                AbstractCustomer customer = parseCustomerFromLine(line);
                if (customer != null) {
                    customers.add(customer);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load customers from file: " + FILE_PATH, e);
        }
    }

    private AbstractCustomer parseCustomerFromLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(";");
        if (parts.length < 4) {
            return null;
        }

        String type = parts[0];
        String id = parts[1];
        String name = parts[2];
        int bonusPoints = Integer.parseInt(parts[3]);

        if ("REGULAR".equals(type)) {
            LocalDate lastOrderDate = null;
            if (parts.length > 4 && !parts[4].trim().isEmpty()) {
                lastOrderDate = LocalDate.parse(parts[4]);
            }
            return new RegularCustomer(id, name, bonusPoints, lastOrderDate);
        } else if ("GOLD".equals(type)) {
            return new GoldCustomer(id, name, bonusPoints);
        }

        return null;
    }

    private void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (AbstractCustomer customer : customers) {
                writer.write(customer.asString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save customers to file: " + FILE_PATH, e);
        }
    }
}