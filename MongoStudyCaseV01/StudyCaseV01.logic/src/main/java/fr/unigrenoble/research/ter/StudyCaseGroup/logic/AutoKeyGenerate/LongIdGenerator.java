/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unigrenoble.research.ter.StudyCaseGroup.logic.AutoKeyGenerate;

import java.util.Random;

public class LongIdGenerator {

    public Long autoKey() {

        Random rng = new Random();
        Long n =  (long) 1000;
        
        Long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);

        return val;

    }

}
