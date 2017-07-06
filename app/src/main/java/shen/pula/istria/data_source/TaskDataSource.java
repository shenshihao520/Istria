package shen.pula.istria.data_source;

/**
 * Created by dell on 2016/11/1.
 */
public interface TaskDataSource {
    Object getStringFromRemote(Object obj);
    Object getStringFromCache(Object obj);
}
