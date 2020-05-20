//package manacle.mcivic.adapter;
//
//
//import android.app.ProgressDialog;
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//
//import java.io.ByteArrayOutputStream;
//
//class test extends AsyncTask<String, Void, String> {
//
//    String sResponse = null;
//
//    @Override
//    protected void onPreExecute() {
//        // TODO Auto-generated method stub
//        super.onPreExecute();
//        dialog = ProgressDialog.show(PhotoUpload.this, "Uploading",
//                "Please wait...", true);
//        dialog.show();
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        try {
//
//            String url = "http://mnbmnbnmb.com/webservice/addphoto?version=apps&user_login_id="+matchId+"&image_1="+ImgData+"&action=save";
//            int i = Integer.parseInt(params[0]);
//            Bitmap bitmap = decodeFile(map.get(i));
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpContext localContext = new BasicHttpContext();
//            HttpPost httpPost = new HttpPost(url);
//            entity = new MultipartEntity();
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            byte[] data = bos.toByteArray();
//
//            entity.addPart("user_login_id", new StringBody("199"));
//            entity.addPart("image_1", new StringBody("10"));
//            entity.addPart("action", new ByteArrayBody(data,
//                    "image/jpeg", params[1]));
//
//            httpPost.setEntity(entity);
//            HttpResponse response = httpClient.execute(httpPost,
//                    localContext);
//            sResponse = EntityUtils.getContentCharSet(response.getEntity());
//
//            System.out.println("sResponse : " + sResponse);
//        } catch (Exception e) {
//            if (dialog.isShowing())
//                dialog.dismiss();
//            Log.e(e.getClass().getName(), e.getMessage(), e);
//
//        }
//        return sResponse;
//    }
//
//    @Override
//    protected void onPostExecute(String sResponse) {
//        try {
//            if (dialog.isShowing())
//                dialog.dismiss();
//
//            if (sResponse != null) {
//                Toast.makeText(getApplicationContext(),
//                        sResponse + " Photo uploaded successfully",
//                        Toast.LENGTH_SHORT).show();
//                count++;
//                if (count < map.size()) {
//                    new ImageUploadTask().execute(count + "", "hm" + count
//                            + ".jpg");
//                }
//            }
//
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getMessage(),
//                    Toast.LENGTH_LONG).show();
//            Log.e(e.getClass().getName(), e.getMessage(), e);
//        }
//
//    }
//}
//
//    public Bitmap decodeFile(String filePath) {
//        // Decode image size
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, o);
//        // The new size we want to scale to
//        final int REQUIRED_SIZE = 1024;
//        // Find the correct scale value. It should be the power of 2.
//        int width_tmp = o.outWidth, height_tmp = o.outHeight;
//        int scale = 1;
//        while (true) {
//            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
//                break;
//            width_tmp /= 2;
//            height_tmp /= 2;
//            scale *= 2;
//        }
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
//        return bitmap;
//    }