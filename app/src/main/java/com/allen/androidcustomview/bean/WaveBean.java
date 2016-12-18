package com.allen.androidcustomview.bean;

/**
 * Created by allen on 2016/12/14.
 * 水波参数的实体
 */

public class WaveBean {

    private int waveLength;

    private int offset;
    private int startOffset;

    private int waveRepeatCount;

    public WaveBean(int waveLength, int startOffset) {
        this.waveLength = waveLength;
        this.startOffset = startOffset;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public int getWaveRepeatCount() {
        return waveRepeatCount;
    }

    public void setWaveRepeatCount(int waveRepeatCount) {
        this.waveRepeatCount = waveRepeatCount;
    }

    public int getWaveLength() {
        return waveLength;
    }

    public void setWaveLength(int waveLength) {
        this.waveLength = waveLength;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
