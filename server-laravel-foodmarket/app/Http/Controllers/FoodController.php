<?php

namespace App\Http\Controllers;

use App\Http\Requests\FoodRequest;
use App\Models\Food;
use Illuminate\Contracts\View\View;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;

class FoodController extends Controller
{
    public function index(): View
    {
        $food = Food::paginate(10);

        return view('food.index', [
            'food' => $food
        ]);
    }

    public function create(): View
    {
        return view('food.create');
    }

    public function store(FoodRequest $request): RedirectResponse
    {
        $data = $request->all();

        $data['picturePath'] = $request->file('picturePath')->store('assets/food', 'public');

        Food::create($data);

        return redirect()->route('food.index');
    }


    public function show(Food $food)
    {
        //
    }


    public function edit(Food $food): View
    {
        return view('food.edit',[
            'item' => $food
        ]);
    }

    public function update(Request $request, Food $food): RedirectResponse
    {
        $data = $request->all();

        if($request->file('picturePath'))
        {
            $data['picturePath'] = $request->file('picturePath')->store('assets/food', 'public');
        }

        $food->update($data);

        return redirect()->route('food.index');
    }


    public function destroy(Food $food): RedirectResponse
    {
        $food->delete();

        return redirect()->route('food.index');
    }
}
