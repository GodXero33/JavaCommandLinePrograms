class ShopDataRecord {
	int id;
	int status;
	String phone;
	int size;
	int qty;
	double amount;

	ShopDataRecord (int recID, int recStatus, String recPhone, int recSize, int recQty, double recAmount) {
		this.id = recID;
		this.status = recStatus;
		this.phone = recPhone;
		this.size = recSize;
		this.qty = recQty;
		this.amount = recAmount;
	}

	ShopDataRecord () {
		this.id = -1;
		this.status = 0;
		this.phone = null;
		this.size = -1;
		this.qty = 0;
		this.amount = 0;
	}
}
