package obj.algorithms;

import obj.ImplicitSurface;
import obj.Object3D;

/**
 * The volume of the implicit Volume
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public class ImplicitVolume implements VolumeMethod {

    @Override
    public double getVolume(Object3D object3d) {
        ImplicitSurface implicitSurface = (ImplicitSurface) object3d;
        return implicitSurface.getVoxelCount()*(Math.pow(implicitSurface.getInterval(),3));
    }

}
