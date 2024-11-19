import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class ShopData {
	final String[] sizes = { "XS", "S", "M", "L", "XL", "XXL" };
	final double[] prices = { 600, 800, 900, 1000, 1100, 1200 };

	ArrayList<Integer> idList = new ArrayList<Integer>();
	ArrayList<Integer> statusList = new ArrayList<Integer>();
	ArrayList<String> phoneList = new ArrayList<String>();
	ArrayList<Integer> sizesList = new ArrayList<Integer>();
	ArrayList<Integer> qtyList = new ArrayList<Integer>();
	ArrayList<Double> amountList = new ArrayList<Double>();

	int current = 0;
	int size = 0;

	public String getOrderID (int id) {
		return String.format("ODR#%05d", id + 1);
	}

	public String getCurrentOrderID () {
		return this.getOrderID(this.current);
	}

	public String getStatus (int status) {
		return status == 0 ? "Processing" : status == 1 ? "Delivering" : "Delevered";
	}

	private static String getRandomPhoneNo (Random rand) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("07");

		for (int i = 0; i < 8; i++) {
			stringBuilder.append(rand.nextInt(10));
		}

		final String output = stringBuilder.toString();
		return output;
	}

	public static void addSampleData (ShopData instance) {
		final Random rand = new Random();

		for (int i = 0; i < 15; i++) {
			instance.idList.add(i);
			instance.statusList.add(0);
			instance.phoneList.add(getRandomPhoneNo(rand));

			final int size = rand.nextInt(instance.sizes.length);
			final int qty = rand.nextInt(49) + 1;
			final double amount = instance.prices[size] * qty;

			instance.sizesList.add(size);
			instance.qtyList.add(qty);
			instance.amountList.add(amount);

			instance.size++;
			instance.current++;
		}
	}

	private int getSizeIndex (String size) {
		for (int i = 0; i < this.sizes.length; i++) {
			if (this.sizes[i].equals(size) || this.sizes[i].equals(size.toUpperCase())) return i;
		}

		return -1;
	}

	public double getAmount (int sizeIndex, int qty) {
		if (sizeIndex < 0 || sizeIndex > this.sizes.length - 1) return 0;
		return this.prices[sizeIndex] * qty;
	}

	public double getAmount (String size, int qty) {
		return this.getAmount(this.getSizeIndex(size), qty);
	}

	public ArrayList<Integer> getAllMatchesFromList (ArrayList<String> arr, String target) {
		ArrayList<Integer> indices = new ArrayList<Integer>();

		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).equals(target)) indices.add(i);
		}

		return indices;
	}

	public void pushRecord (String phone, String size, int qty, double amount) {
		this.idList.add(this.current);
		this.statusList.add(0);
		this.phoneList.add(phone);
		this.sizesList.add(this.getSizeIndex(size));
		this.qtyList.add(qty);
		this.amountList.add(amount);

		this.current++;
		this.size++;
	}

	public void deleteRecord (int id) {
		int index = this.idList.indexOf(id);

		if (index == -1) return;

		this.idList.remove(index);
		this.statusList.remove(index);
		this.phoneList.remove(index);
		this.sizesList.remove(index);
		this.qtyList.remove(index);
		this.amountList.remove(index);

		this.size--;
	}

	public ShopDataRecord getRecord (int id) {
		int index = this.idList.indexOf(id);

		if (index != -1) {
			return new ShopDataRecord(
				this.idList.get(index),
				this.statusList.get(index),
				this.phoneList.get(index),
				this.sizesList.get(index),
				this.qtyList.get(index),
				this.amountList.get(index)
			);
		}

		return new ShopDataRecord();
	}

	public HashMap<String, ShopDataRecord> getMergedRecordsByPhone () {
		final HashMap<String, ShopDataRecord> map = new HashMap<>();

		for (int i = 0; i < this.phoneList.size(); i++) {
			String phone = this.phoneList.get(i);
			ShopDataRecord record;

			if (map.containsKey(phone)) {
				record = map.get(phone);
			} else {
				record = new ShopDataRecord();
				record.phone = phone;
				map.put(phone, record);
			}

			record.qty += this.qtyList.get(i);
			record.amount += this.amountList.get(i);
		}

		return map;
	}

	public HashMap<String, int[]> getRecordsAllDetails () {
		HashMap<String, int[]> map = new HashMap<>();

		for (int i = 0; i < this.idList.size(); i++) {
			String phone = this.phoneList.get(i);
			int size = this.sizesList.get(i);
			int qty = this.qtyList.get(i);
			int[] sizes;

			if (map.containsKey(phone)) {
				sizes = map.get(phone);
			} else {
				sizes = new int[7];
				map.put(phone, sizes);
			}

			sizes[size] += qty;
			sizes[6] += qty * this.prices[size];
		}

		return map;
	}

	public HashMap<String, int[]> getItemsDetails () {
		HashMap<String, int[]> map = new HashMap<>();

		for (int i = 0; i < this.idList.size(); i++) {
			int size = this.sizesList.get(i);
			String sizeStr = this.sizes[size];
			int[] record;

			if (map.containsKey(sizeStr)) {
				record = map.get(sizeStr);
			} else {
				record = new int[2];
				map.put(sizeStr, record);
			}

			record[0] += this.qtyList.get(i);
			record[1] += this.amountList.get(i);
		}

		for (String size : this.sizes) {
			if (!map.containsKey(size)) {
				map.put(size, new int[] { 0, 0 });
			}
		}

		return map;
	}
}
