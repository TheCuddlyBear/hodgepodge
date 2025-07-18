package me.thecuddlybear.hodgepodge.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class HodgepodgeDayCounterSavedData extends SavedData {
    private int dayCounter;
    private long lastDayTime;

    public static final SavedDataType<HodgepodgeDayCounterSavedData> ID = new SavedDataType<>(
            // The identifier of the saved data
            // Used as the path within the level's `data` folder
            "example",
            // The initial constructor
            HodgepodgeDayCounterSavedData::new,
            // The codec used to serialize the data
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("day_counter").forGetter(sd -> sd.dayCounter),
                    Codec.LONG.fieldOf("last_day_time").forGetter(sd -> sd.lastDayTime)

            ).apply(instance, HodgepodgeDayCounterSavedData::new)),
            null
    );

    HodgepodgeDayCounterSavedData(){
        this.dayCounter = 0;
        this.lastDayTime = 0;
    }

    HodgepodgeDayCounterSavedData(int dayCounter, long lastDayTime){
        this.dayCounter = dayCounter;
        this.lastDayTime = lastDayTime;
    }

    // Getters and setters
    public int getDayCounter() { return dayCounter; }
    public void setDayCounter(int dayCounter) {
        this.dayCounter = dayCounter;
        setDirty(); // Mark for saving
    }

    public long getLastDayTime() { return lastDayTime; }
    public void setLastDayTime(long lastDayTime) {
        this.lastDayTime = lastDayTime;
        setDirty(); // Mark for saving
    }

}
