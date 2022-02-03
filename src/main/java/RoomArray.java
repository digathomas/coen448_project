public class RoomArray {
	
	private boolean[][] roomArray;
	private int size;
	
	//constructor
	public RoomArray(int s){
		if(s > 0) {
			this.size = s;
			roomArray = new boolean[s][s];
			for(int i = 0; i < roomArray.length; i++) {
				for(int j = 0; j < roomArray[0].length; j++) {
					roomArray[i][j] = false;
				}
			}
		}
	}

	public void print() {
		if(roomArray != null) {
			for(int y = roomArray.length-1; y >= 0; y--) {
				System.out.print(y + "  ");
				for(int x = 0; x < roomArray[0].length; x++) {
					if(roomArray[x][y] == true) {
						System.out.print(" * ");
						continue;
					}
					System.out.print("   ");
				}
				System.out.print("\n");
			}
			System.out.print("   ");
			for(int x = 0; x < roomArray[0].length; x++) {
				System.out.print(" " + x + " ");
			}
			System.out.print("\n");
		}
	}
	
	public void trace(int x, int y) {
		roomArray[x][y] = true;
	}
	
	//getters

	public boolean[][] getRoomArray() {
		return roomArray;
	}

	public int getSize() {
		return this.size;
	}
}
