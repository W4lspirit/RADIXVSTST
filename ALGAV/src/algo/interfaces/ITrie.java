package algo.interfaces;

import java.util.List;

public interface ITrie {

    //  une fonction de recherche d’un mot dans un dictionnaire :

    /**
     * @param word
     * @return
     */
    boolean search(String word);

    //  une fonction qui compte les mots présents dans le dictionnaire :

    /**
     * @return
     */
    int ComptageMots();

    //  une fonction qui liste les mots du dictionnaire dans l’ordre
    // alphabétique :

    /**
     * @return
     */
    List<String> ListeMots();

    //  une fonction qui compte les pointeurs vers Nil :

    /**
     * @return
     */
    int ComptageNil();

    //  une fonction qui calcule la hauteur de l’arbre :

    /**
     * @return
     */
    int Hauteur();

    // 1 une fonction qui calcule la profondeur moyenne des feuilles de
    // l’arbre :

    /**
     * @return
     */
    int ProfondeurMoyenne();

	/*
     *  une fonction qui prend un mot A en argument et qui indique de combien
	 * de mots du dictionnaire le mot A est préfixe. Ainsi pour l’exemple de
	 * base, le mot dactylo est préfixe de deux mots de l’arbre
	 * (dactylographie et dactylo). Noter que le mot A n’est pas forcément un
	 * mot de l’arbre :
	 */

    /**
     * @param word
     * @return
     */
    int Prefixe(String word);

    //  une fonction qui prend un mot en argument et qui le supprime de
    // l’arbre s’il y figure :

    /**
     * @param word
     * @return
     */
    ITrie Suppression(String word);
}
