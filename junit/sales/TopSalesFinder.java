package junit.sales;

public class TopSalesFinder {
    public static void main(String[] args) {

    }

    private SalesRecord[] records;
    private int capacity;
    private int size;

    public TopSalesFinder() {
        capacity = 10;
        size = 0;
        records = new SalesRecord[capacity];
    }

    public void registerSale(SalesRecord record) {
        if (size >= capacity) {
            capacity *= 2;
            SalesRecord[] newRecords = new SalesRecord[capacity];
            for (int i = 0; i < size; i++) {
                newRecords[i] = records[i];
            }
            records = newRecords;
        }
        records[size] = record;
        size++;
    }

    public SalesRecordResult[] findItemsSoldOver(int amount) {
        // Loome dünaamilise massiivi toodete kogutulu hoidmiseks
        ProductData productData = new ProductData();

        // Arvutame iga toote kogutulu
        for (int i = 0; i < size; i++) {
            SalesRecord record = records[i];
            int total = record.productPrice() * record.itemsSold();
            productData.addOrUpdateProduct(record.productId(), total);
        }

        return productData.getResultsOverAmount(amount);
    }

    private static class ProductData {
        private String[] productIds;
        private int[] productTotals;
        private int count;

        ProductData() {
            productIds = new String[10];
            productTotals = new int[10];
            count = 0;
        }

        void addOrUpdateProduct(String productId, int total) {
            int productIndex = findProductIndex(productId);

            if (productIndex >= 0) {
                productTotals[productIndex] += total;
            } else {
                if (count >= productIds.length) {
                    expandArrays();
                }
                productIds[count] = productId;
                productTotals[count] = total;
                count++;
            }
        }

        private int findProductIndex(String productId) {
            for (int i = 0; i < count; i++) {
                if (productIds[i].equals(productId)) {
                    return i;
                }
            }
            return -1;
        }

        private void expandArrays() {
            String[] newIds = new String[productIds.length * 2];
            int[] newTotals = new int[productTotals.length * 2];

            for (int i = 0; i < count; i++) {
                newIds[i] = productIds[i];
                newTotals[i] = productTotals[i];
            }

            productIds = newIds;
            productTotals = newTotals;
        }

        SalesRecordResult[] getResultsOverAmount(int amount) {
            int matchCount = 0;
            for (int i = 0; i < count; i++) {
                if (productTotals[i] > amount) {
                    matchCount++;
                }
            }

            SalesRecordResult[] results = new SalesRecordResult[matchCount];
            int index = 0;
            for (int i = 0; i < count; i++) {
                if (productTotals[i] > amount) {
                    results[index++] = new SalesRecordResult(productIds[i], productTotals[i]);
                }
            }

            return results;
        }
    }

    public void removeSalesRecordsFor(String id) {
        int writeIndex = 0;

        // Kopeerime ainult need kirjed, mis ei vasta eemaldatavale ID-le
        for (int readIndex = 0; readIndex < size; readIndex++) {
            if (!records[readIndex].productId().equals(id)) {
                records[writeIndex] = records[readIndex];
                writeIndex++;
            }
        }

        size = writeIndex;
    }

    public SalesRecord[] getAllRecordsPaged(int pageNumber, int pageSize) {
        int startIndex = pageNumber * pageSize;

        // Kui lehekülg on andmete piiridest väljas
        if (startIndex >= size) {
            return new SalesRecord[0];
        }

        // Arvutame tegeliku lehe suuruse
        int actualPageSize = Math.min(pageSize, size - startIndex);
        SalesRecord[] page = new SalesRecord[actualPageSize];

        // Kopeerime vajalikud kirjed
        for (int i = 0; i < actualPageSize; i++) {
            page[i] = records[startIndex + i];
        }

        return page;
    }

    public int getRecordCount() {
        return size;
    }

    public void removeRecord(String id) {
        for (int i = 0; i < size; i++) {
            if (records[i].recordId().toString().equals(id)) {
                // Nihutame kõik järgmised kirjed ühe koha võrra vasakule
                for (int j = i; j < size - 1; j++) {
                    records[j] = records[j + 1];
                }
                size--;
                return;
            }
        }
    }
}