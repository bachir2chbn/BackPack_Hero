package controller;
import java.awt.Color;

import com.github.forax.zen.Application;

import model.items.*;
import model.items.MeleeWeapon;
import model.items.Rarity;
import model.items.WeaponName;
import view.BackpackView;

public class Main {

	public static void main(String[] args) {
		
		boolean[][] swordShape = {
		    {false, false, false},
		    {false, true, false},
		    {false, false, false}
		    // ligne horizontale
		};
		Item sword = new MeleeWeapon(WeaponName.machete, 2, Rarity.commun, swordShape, 0, "azul");
		System.out.println( sword);
		sword = sword.rotate();
		System.out.println(sword);
//	  Application.run(Color.WHITE, BackpackController:: backpack );

	}


}

