package Model;

import View.ModelListener;

public interface GamePublisher {
    void registerObserver(ModelListener observer);
    void removeObserver(ModelListener observer);
    void notifyObservers();
}