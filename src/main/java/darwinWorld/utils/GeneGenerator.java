package darwinWorld.utils;

import darwinWorld.enums.MoveRotation;

import java.util.ArrayList;
import java.util.List;

public class GeneGenerator {
    public List<MoveRotation> forLength(int genesLenght){
        List<MoveRotation> moves = new ArrayList<>();
        for(int i = 0; i < genesLenght; i++)
            moves.add(MoveRotation.randomMoveRotation());
        return moves;
    }
}
