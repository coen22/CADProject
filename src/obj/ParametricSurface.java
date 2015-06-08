package obj;

import java.util.List;
import obj.parametric_formula.FormulaAbstract;

/**
 *
 * @author Kareem Horstink
 */
public class ParametricSurface extends Object3D {

    private FormulaAbstract x;
    private FormulaAbstract y;
    private FormulaAbstract z;

    ;

    public ParametricSurface(FormulaAbstract x, FormulaAbstract y, FormulaAbstract z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public List<Vertex> getVerts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Triangle> getTris() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/*
 http://tutorial.math.lamar.edu/Classes/CalcIII/ParametricSurfaces.aspx
 http://en.wikipedia.org/wiki/Parametric_surface
 */
