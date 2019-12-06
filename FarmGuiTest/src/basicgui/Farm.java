/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgui;

/**
 *
 * @author user
 */
public class Farm {
    private String weight,breedtype;
    final private String cattleid;
    
    Farm(String weight,String breedtype,String cattleid){
        this.weight = weight;
        this.breedtype = breedtype;
        this.cattleid = cattleid;
    }
    void setBreed(String breedtype){
        this.breedtype = breedtype;
    }
    void setWeight(String phone){
        this.weight = weight;
    }
    String getWeight(){
        return weight;
    }
    String getBreed(){
        return breedtype;
    }
    String getId(){
        return cattleid;
    }
}
