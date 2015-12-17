package br.ufc.es.retry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17/12/2015.
 */
public class Group {

    public String string;
    public final List<String> children = new ArrayList<>();

    public Group(String string){
        this.string = string;
    }
}
