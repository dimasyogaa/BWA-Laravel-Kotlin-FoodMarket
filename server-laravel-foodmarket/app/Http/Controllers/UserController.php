<?php

namespace App\Http\Controllers;

use App\Http\Requests\UserRequest;
use App\Models\User;
use Illuminate\Contracts\View\View;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Log;

class UserController extends Controller
{

    public function index(): View
    {
        $user = User::paginate(10);

        return view('users.index', [
            'user' => $user
        ]);
    }


    public function create(): View
    {
        return view('users.create');
    }


    public function store(UserRequest $request): RedirectResponse
    {
        $data = $request->all();

        $data['profile_photo_path'] = $request->file('profile_photo_path')->store('assets/user', 'public');

        User::create($data);

        return redirect()->route('users.index');
    }


    public function show(User $user)
    {
        //
    }

    public function edit(User $user): View
    {
        return view('users.edit',[
            'item' => $user
        ]);
    }

    public function update(UserRequest $request, User $user): RedirectResponse
    {
        $data = $request->all();

        if($request->file('picturePath'))
        {
            $data['profile_photo_path'] = $request->file('profile_photo_path')->store('assets/user', 'public');
        }

        $user->update($data);

        return redirect()->route('users.index');
    }

    public function destroy(User $user): RedirectResponse
    {
        $user->delete();

        return redirect()->route('users.index');
    }
}
