package controller;


import model.items.*;



public class Main {

	public static void main(String[] args) {
		
		boolean[][] swordShape = {
		    {false, false, false},
		    {false, true, false},
		    {false, false, false}
		    // ligne horizontale
		};
		Item sword = new MeleeWeapon(WeaponName.machete, 2, Rarity.commun, swordShape, 0, "azul");
		System.out.println(sword);
		sword = sword.rotate();
		System.out.println(sword);
	}

}

