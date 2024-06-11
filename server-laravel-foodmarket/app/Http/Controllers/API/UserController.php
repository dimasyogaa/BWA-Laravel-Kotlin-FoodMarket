<?php

namespace App\Http\Controllers\API;

use App\Actions\Fortify\PasswordValidationRules;
use App\Helpers\ResponseFormatter;
use App\Http\Controllers\Controller;
use App\Models\User;
use Exception;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Validator;

class UserController extends Controller
{
    use PasswordValidationRules;

    /**
     * @throws Exception
     */
    public function login(Request $request): JsonResponse
    {
        try {

            // validasi input
            $request->validate([
                'email' => 'email|required',
                'password' => 'required'
            ]);

            // mengecek credential login
            $credentials = request(['email', 'password']);
            if (!Auth::attempt($credentials)) {
                return ResponseFormatter::error([
                    'message' => 'Unauthorized'
                ], 'Authentication Failed', 500);
            }

            $user = User::where('email', $request->email)->first();
            if (!Hash::check($request->password, $user->password)) {
                throw new Exception('Invalid Credentials');
            }

            $tokenResult = $user->createToken('authToken')->plainTextToken;
            return ResponseFormatter::success([
                'access_token' => $tokenResult,
                'token_type' => 'Bearer',
                'user' => $user
            ], 'Authenticated');
        } catch (Exception $error) {
            return ResponseFormatter::error([
                'message' => 'Something went wrong',
                'error' => $error,
            ], 'Authentication Failed', 500);
        }
    }

    public function register(Request $request): JsonResponse
    {
        try {
            // Validasi input
            $request->validate([
                'name' => ['required', 'string', 'max:255'],
                'email' => ['required', 'string', 'email', 'max:255', 'unique:users'],
                'password' => ['required', 'string', 'min:8'], // Atur aturan kata sandi sesuai kebutuhan
            ]);

            // Buat pengguna baru
            $user = User::create([
                'name' => $request->name,
                'email' => $request->email,
                'address' => $request->address ?? '', // Opsional, sesuaikan jika field ini tidak wajib
                'houseNumber' => $request->houseNumber ?? '', // Opsional
                'phoneNumber' => $request->phoneNumber ?? '', // Opsional
                'city' => $request->city ?? '', // Opsional
                'password' => Hash::make($request->password),
            ]);

            $user = User::where('email', $request->email)->first();

            // Buat token otentikasi untuk API
            $tokenResult = $user->createToken('authToken')->plainTextToken;

            // Kembalikan respons berhasil dengan token dan detail pengguna
            return ResponseFormatter::success([
                'access_token' => $tokenResult,
                'token_type' => 'Bearer',
                'user' => $user
            ], 'User Registered and Logged In');
        } catch (Exception $error) {
            // Tangani error dan kembalikan respons error
            return ResponseFormatter::error([
                'message' => 'Something went wrong',
                'error' => $error->getMessage(),
            ], 'Registration Failed', 500);
        }
    }

    public function logout(Request $request): JsonResponse
    {
        $token = $request->user()->currentAccessToken()->delete();

        return ResponseFormatter::success($token,'Token Revoked');
    }

    public function fetch(Request $request): JsonResponse
    {
        return ResponseFormatter::success($request->user(),'Data profile user berhasil diambil');
    }

    public function updateProfile(Request $request): JsonResponse
    {
        $data = $request->all();

        $user = Auth::user();
        $user->update($data);

        return ResponseFormatter::success($user,'Profile Updated');
    }


    public function updatePhoto(Request $request)
    {

        $this->validatorImage($request);

        $user = Auth::user();


        if ($request->file('file')) {

            $file = $request->file->store('assets/user', 'public');

            // Simpan foto ke database(urlnya)
            $user = Auth::user();
            $user->profile_photo_path = $file;
            $user->update();

            return ResponseFormatter::success([$file],'File successfully uploaded');
        }
    }

    private function validatorImage(Request $request) {
        $validator = Validator::make($request->all(), [
            'file' => 'required|image|max:2048',
        ]);
        if ($validator->fails()) {
            return ResponseFormatter::error(['error'=>$validator->errors()], 'Update Photo Fails', 401);
        }
    }
}
