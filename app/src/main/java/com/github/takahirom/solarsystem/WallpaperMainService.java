package com.github.takahirom.solarsystem;

import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class WallpaperMainService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new PlanetEngine();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class PlanetEngine extends Engine {
        private ZoomImageView zoomImageView;
        private SurfaceHolder holder;
        private Handler handler;

        PlanetEngine() {
        }


        @Override
        public void onCreate(final SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
            zoomImageView = new ZoomImageView(new ContextThemeWrapper(WallpaperMainService.this, R.style.AppTheme));
            zoomImageView.setImageResource(R.drawable.animation_planet_vector_drawable);
            ((Animatable) zoomImageView.getDrawable()).start();

            handler = new Handler();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            String action = "";
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    action = "ACTION_DOWN";
                    break;
                case MotionEvent.ACTION_UP:
                    action = "ACTION_UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    action = "ACTION_MOVE";
                    break;
                case MotionEvent.ACTION_CANCEL:
                    action = "ACTION_CANCEL";
                    break;
            }

            Log.v("MotionEvent",
                    "action = " + action + ", " +
                            "count:" + event.getPointerCount() + ", " +
                            "x = " + String.valueOf(event.getX()) + ", " +
                            "y = " + String.valueOf(event.getY()));

            zoomImageView.dispatchTouchEvent(event);
        }

        @Override
        public void onSurfaceChanged(final SurfaceHolder holder, int format, final int width, final int height) {
            super.onSurfaceChanged(holder, format, width, height);
            this.holder = holder;
            zoomImageView.layout(0, 0, width, height);
        }

        private Runnable refreshRunnable = new Runnable() {
            public void run() {
                refresh();
                handler.removeCallbacks(refreshRunnable);
                handler.postDelayed(refreshRunnable, 33);
            }
        };

        private void refresh() {
            if (holder == null) {
                return;
            }
            Canvas canvas = holder.lockCanvas();
            if (canvas == null) {
                return;
            }
            canvas.drawColor(getResources().getColor(R.color.background_material_dark));
            zoomImageView.draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);

            handler.post(refreshRunnable);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                final Animatable drawable = (Animatable) zoomImageView.getDrawable();
                drawable.stop();
                drawable.start();

                handler.removeCallbacks(refreshRunnable);
                handler.post(refreshRunnable);
            } else {
                handler.removeCallbacks(refreshRunnable);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);

            handler.removeCallbacks(refreshRunnable);
        }
    }

}