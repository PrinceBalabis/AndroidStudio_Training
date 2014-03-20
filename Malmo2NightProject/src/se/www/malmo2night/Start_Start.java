package se.www.malmo2night;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragment one. This one is the one that is showed when the app launches.
 * The start-fragment.
 */
public class Start_Start extends Fragment {
    Camera camera;
    boolean isFlashOn;
    boolean hasFlash;
    Parameters params;
    MediaPlayer mp;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.start_start, container, false);
        //		showPopupDialog();
//		Button button = (Button) rootView.findViewById(R.id.start_blixt);
//
//		hasFlash = getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//		if (!hasFlash) {
//			button.setVisibility(View.GONE);
//		}
//		button.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				if (isFlashOn) {
//					turnOffFlash();
//				} else {
//					turnOnFlash();
//				}
//			}
//		});
//
//		initateFlash();
        return rootView;
    }

    private void showPopupDialog() {
        FragmentManager fm = getFragmentManager();
        Start_Start_PopupDialog popup = new Start_Start_PopupDialog();
        popup.show(fm, "popupdialog");
    }


    //BLixt
    private void initateFlash() {
        getCamera();
    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
            }
        }
    }


    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
    }

    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }
}