package com.example.mystepsapp;

import java.util.Comparator;
import java.util.*;

public class StepComparator implements Comparator {


    @Override
    public int compare(Object o1, Object o2) {
        PositionModel p1=(PositionModel)o1;
        PositionModel p2=(PositionModel)o2;

        if(Integer.parseInt(p1.getTotalsteps())== Integer.parseInt(p2.getTotalsteps()))
            return 0;
        else if(Integer.parseInt(p1.getTotalsteps()) > Integer.parseInt(p2.getTotalsteps()))
            return -1;
        else
            return 1;
    }
}
