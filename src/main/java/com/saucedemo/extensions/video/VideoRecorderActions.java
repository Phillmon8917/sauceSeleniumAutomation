package com.saucedemo.extensions.video;

import io.github.phillmon.selenium.utils.logging.LoggerUtil;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.MIME_AVI;
import static org.monte.media.VideoFormatKeys.QualityKey;


public final class VideoRecorderActions {
    private static final ThreadLocal<ScreenRecorder> RECORDERS = new ThreadLocal<>();

    private VideoRecorderActions() {
    }

    public static void startRecording(Path movieFolder, String label) {
        try {
            java.nio.file.Files.createDirectories(movieFolder);
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();

            ScreenRecorder recorder = new ScreenRecorder(gc, gc.getBounds(),
                    new Format(MediaTypeKey, org.monte.media.FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, org.monte.media.FormatKeys.MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24,
                            FrameRateKey, org.monte.media.math.Rational.valueOf(10), QualityKey, 1.0f,
                            KeyFrameIntervalKey, 10 * 60),
                    new Format(MediaTypeKey, org.monte.media.FormatKeys.MediaType.VIDEO, EncodingKey, "black",
                            FrameRateKey, org.monte.media.math.Rational.valueOf(30)),
                    null, movieFolder.toFile());

            recorder.start();
            RECORDERS.set(recorder);
            LoggerUtil.info("VideoRecorderActions started recording for: " + label);
        } catch (IOException | AWTException e) {
            LoggerUtil.warning("VideoRecorderActions could not start recording for '" + label + "': " + e.getMessage());
            RECORDERS.remove();
        }
    }

    public static Optional<File> stopRecording(String label) {
        ScreenRecorder recorder = RECORDERS.get();
        if (recorder == null) {
            return Optional.empty();
        }
        try {
            recorder.stop();
            List<File> files = recorder.getCreatedMovieFiles();
            LoggerUtil.info("VideoRecorderActions stopped recording for: " + label
                    + " (" + files.size() + " file(s) produced)");
            return files.isEmpty() ? Optional.empty() : Optional.of(files.get(0));
        } catch (IOException e) {
            LoggerUtil.warning("VideoRecorderActions failed to stop recording for '" + label + "': " + e.getMessage());
            return Optional.empty();
        } finally {
            RECORDERS.remove();
        }
    }
}
