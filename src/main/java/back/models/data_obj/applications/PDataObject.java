package back.models.data_obj.applications;

/**
 * Created by jdwinters on 7/19/17.
 */
public interface PDataObject<T extends PDataObject<T>>  {

    void setTag(int tagz);
    int  getTag();
}
