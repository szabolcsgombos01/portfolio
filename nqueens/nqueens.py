

from enum import Flag

import copy
from functools import partial

from typing import Any, Generator, Optional, Type, Union
from abc import ABC, abstractmethod

import PySimpleGUI as sg

from framework.board import Board
from framework.gui import BoardGUI

QUEEN_IMAGE_PATH = "tiles/queen_scaled.png"
BLANK_IMAGE_PATH = "tiles/chess_blank_scaled.png"
IMAGE_SIZE = (64, 64)

sg.ChangeLookAndFeel("SystemDefault")

SquareState = Flag("SquareState", "W B Q U")


class Square:

    def __init__(self, initial_state: SquareState):
        self.state = initial_state

    def has_queen(self) -> bool:
        """Returns True if the square has a queen on it."""
        return bool(self.state & SquareState.Q)

    def set_queen(self):
        """Sets (puts) a queen on the square."""
        self.state = self.state | SquareState.Q

    def clear_queen(self):
        """Clears (removes) the queen from the square."""
        self.state = self.state & ~SquareState.Q

    def is_under_attack(self) -> bool:
        """Returns True if the square is under attack."""
        return bool(self.state & SquareState.U)

    def set_attack(self):
        """Sets the square under attack."""
        self.state = self.state | SquareState.U

    def clear_attack(self):
        """Clears the square from attack."""
        self.state = self.state & ~SquareState.U


class ChessBoard(Board):
    """The representation of the chessboard. In our search problem, the state is the
    chessboard."""

    def _default_state_for_coordinates(self, i: int, j: int) -> Square:
        # white or black
        return Square(SquareState.W) if (i + j) % 2 == 0 else Square(SquareState.B)

    def is_under_attack(self, row_ind: int, col_ind: int) -> bool:
        """Checks whether a square is under attack."""
        for i, row in enumerate(self.board):
            for j, square in enumerate(row):
                if not (row_ind == i and col_ind == j):
                    if square.has_queen():
                        if (
                            row_ind == i
                            or col_ind == j
                            or abs(row_ind - i) == abs(col_ind - j)
                        ):
                            return True
        return False

    def update_attack(self) -> None:
        """Updates the whole board with all the attacked squares of the queens on the board."""
        for row_ind, row in enumerate(self.board):
            for col_ind, square in enumerate(row):
                if self.is_under_attack(row_ind, col_ind):
                    self.board[row_ind][col_ind].set_attack()
                else:
                    self.board[row_ind][col_ind].clear_attack()

    def nqueens(self) -> int:
        """Returns the number of queens on the board."""
        return sum(1 for row in self.board for square in row if square.has_queen())


class QueensProblem(ABC):
    """The abstract n-queens problem. All of the search problems are subclassed from
    this class."""

    def __init__(self, n: int):
        self.n = n
        self.board = ChessBoard(n, n)

    def start_state(self) -> ChessBoard:
        """Returns the start state."""
        return self.board

    def is_goal_state(self, state: ChessBoard) -> bool:
        """Returns true is state is a goal state, false otherwise."""
        board = state
        nqueens = 0
        for i, row in enumerate(board):
            for j, square in enumerate(row):
                if square.has_queen():
                    if board.is_under_attack(i, j):
                        return False
                    else:
                        nqueens += 1
        return nqueens == self.n

    @abstractmethod
    def next_states(self, state: ChessBoard) -> Generator[ChessBoard, None, None]:
        """Returns the possible next states for state.
        This will be different in the different search problems."""
        pass

    def _to_drawable(self, state: ChessBoard) -> ChessBoard:
        """As the state is a board, we can just return it as it's already drawable."""
        return state


class QueensProblemNoAttack(QueensProblem):
    """This search problem doesn't check attacks and puts Queens arbitrarily on the board."""

    def next_states(self, state: ChessBoard) -> Generator[ChessBoard, None, None]:
        board = state
        if board.nqueens() >= self.n:
            return None
        for i, row in enumerate(board):
            for j, square in enumerate(row):
                if not square.has_queen():
                    next_board = copy.deepcopy(board)
                    next_board[i, j].set_queen()
                    next_board.update_attack()
                    yield next_board


RowByRowState = tuple[ChessBoard, int]
State = Union[ChessBoard, RowByRowState]


def backtrack(
    problem: QueensProblem, step_by_step: bool = False
) -> Optional[Generator[State, None, None]]:
    """The BT1 algorithm implemented recursively with an inner funcion."""
    state = problem.start_state()
    path = []

    def backtrack_recursive(state: State) -> Optional[list[State]]:
        """The inner function that implements BT1."""
        path.append(state)
        if problem.is_goal_state(state):
            return []
        for next_state in problem.next_states(state):
            solution = backtrack_recursive(next_state)
            if solution is not None:
                return [next_state] + solution
        return None

    result = backtrack_recursive(state)
    if result:
        return (r for r in path) if step_by_step else (r for r in result)
    else:
        return None


# SEARCH PROBLEMS (STATE SPACES)
# implement the "next_states" methods

class QueensProblemAttack(QueensProblem):
    """This search problem checks attacks, but puts Queens arbitrarily on the board."""

    def next_states(self, state: ChessBoard) -> Generator[ChessBoard, None, None]:
        board = state
        if board.nqueens() >= self.n:
            return None
        for i, row in enumerate(board):
            for j, square in enumerate(row):
                if not square.is_under_attack() and not square.has_queen():
                    next_board = copy.deepcopy(board)
                    next_board[i, j].set_queen()
                    next_board.update_attack()
                    yield next_board


class QueensProblemRowByRow(QueensProblem):
    """This search problem checks attacks and puts queens on the board row by row.
    The state is the board and the next row to put a queen in."""

    def start_state(self) -> RowByRowState:
        return self.board, 0

    def next_states(self, state: RowByRowState) -> Generator[RowByRowState, None, None]:
        # the state consists of a board and the row index of the next row in which there is no queen
        board, row_ind = state
        for j, square in enumerate(board[row_ind]):
            if not square.is_under_attack():
                next_board = copy.deepcopy(board)
                next_board[row_ind, j].set_queen()
                next_board.update_attack()
                yield next_board, row_ind + 1

    def is_goal_state(self, state: RowByRowState) -> bool:
        board, row_ind = state
        return row_ind == board.m

    def _to_drawable(self, state: RowByRowState) -> ChessBoard:
        board, row_ind = state
        return board


queens_draw_dict = {
    SquareState.W: ("", ("black", "white"), BLANK_IMAGE_PATH),
    SquareState.B: ("", ("black", "lightgrey"), BLANK_IMAGE_PATH),
    SquareState.U | SquareState.W: ("", ("black", "red"), BLANK_IMAGE_PATH),
    SquareState.U | SquareState.B: ("", ("black", "#700000"), BLANK_IMAGE_PATH),
    SquareState.W | SquareState.Q: ("", ("black", "white"), QUEEN_IMAGE_PATH),
    SquareState.B | SquareState.Q: ("", ("black", "lightgrey"), QUEEN_IMAGE_PATH),
    SquareState.U
    | SquareState.W
    | SquareState.Q: ("", ("black", "white"), QUEEN_IMAGE_PATH),
    SquareState.U
    | SquareState.B
    | SquareState.Q: ("", ("black", "lightgrey"), QUEEN_IMAGE_PATH),
}

algorithms = {
    "Backtrack - step by step": partial(backtrack, step_by_step=True),
    "Backtrack - just the solution": backtrack,
}

state_spaces: dict[str, Type[QueensProblem]] = {
    "Don't check attacks": QueensProblemNoAttack,
    "Check attacks": QueensProblemAttack,
    "Row by row": QueensProblemRowByRow,
}

board_sizes = {"4x4": 4, "6x6": 6, "8x8": 8, "10x10": 10}


def create_window(board_gui):

    layout = [
        [sg.Column(board_gui.board_layout)],
        [
            sg.Frame(
                "Algorithm settings",
                [
                    [
                        sg.T("Algorithm: ", size=(12, 1)),
                        sg.Combo(
                            [
                                "Backtrack - just the solution",
                                "Backtrack - step by step",
                            ],
                            key="algorithm",
                            readonly=True,
                            default_value="Backtrack - just the solution",
                        ),
                    ],
                    [
                        sg.T("State space: ", size=(12, 1)),
                        sg.Combo(
                            ["Don't check attacks", "Check attacks", "Row by row"],
                            key="state_space",
                            readonly=True,
                            default_value="Check attacks"
                        ),
                    ],
                    [sg.Button("Change", key="change_algorithm")],
                ],
            ),
            sg.Frame(
                "Problem settings",
                [
                    [
                        sg.T("Board size: ", size=(12, 1)),
                        sg.Combo(
                            ["4x4", "6x6", "8x8", "10x10"],
                            key="board_size",
                            readonly=True,
                            default_value="4x4"
                        ),
                    ],
                    [sg.Button("Change", key="change_problem")],
                ],
            ),
        ],
        [sg.T("Steps: "), sg.T("0", key="steps",
                               size=(7, 1), justification="right")],
        [sg.Button("Restart"), sg.Button("Step"),
         sg.Button("Go!"), sg.Button("Exit")],
    ]

    window = sg.Window(
        "N queens problem",
        layout,
        default_button_element_size=(10, 1),
        auto_size_buttons=False,
        location=(0, 0)
    )
    return window


starting = True
go = False
steps = 0

board_size = 4
board_gui = BoardGUI(ChessBoard(board_size, board_size), queens_draw_dict,
                     lambda x: x.state)
window = create_window(board_gui)

while True:  # Event Loop
    event, values = window.Read(0)
    if event is None or event == "Exit" or event == sg.WIN_CLOSED:
        break
    window.Element("Go!").Update(text="Stop!" if go else "Go!")
    if event == "change_algorithm" or starting:
        queens_problem = state_spaces[values["state_space"]](board_size)
        algorithm: Any = algorithms[values["algorithm"]]
        board_gui.board = queens_problem.board
        path = algorithm(queens_problem)
        steps = 0
        starting = False
        stepping = True
    if event == "change_problem":
        board_size = board_sizes[values["board_size"]]
        queens_problem = state_spaces[values["state_space"]](board_size)
        board_gui.board = queens_problem.board
        board_gui.create()
        path = algorithm(queens_problem)
        steps = 0
        window.Close()
        window = create_window(board_gui)
        window.Finalize()
        window.Element("algorithm").Update(values["algorithm"])
        window.Element("state_space").Update(values["state_space"])
        window.Element("board_size").Update(values["board_size"])
        stepping = True
        continue
    if event == "Restart":
        queens_problem = state_spaces[values["state_space"]](board_size)
        board_gui.board = queens_problem.board
        path = algorithm(queens_problem)
        steps = 0
        stepping = True
    if (event == "Step" or go or stepping) and path:
        try:
            state = next(path)
            steps += 1
            window.Element("steps").Update(f"{steps}")
        except StopIteration:
            pass
        board = queens_problem._to_drawable(state)
        board_gui.board = board
        board_gui.update()
        stepping = False
    if event == "Go!":
        go = not go

window.Close()
