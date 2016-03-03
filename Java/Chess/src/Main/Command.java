package Main;

/**
 * Created by Jacob Zamora on 2/12/2016.
 */
public class Command {
    private String _target;
    private String _subTarget;
    private String _fullName;
    private String _action;
    private String _subAction;
    private CommandType _commandType;

    public Command(String target, String action, CommandType commandType)
    {
        _target = target;
        _action = action;
        _commandType = commandType;
        if(commandType == CommandType.Place)
        {
            if(_target.charAt(1) == 'D')
            {
                _fullName = "Black";
            }
            else if(_target.charAt(1) == 'L')
            {
                _fullName = "White";
            }
            if(_target.charAt(0) == 'P')
            {
                _fullName += " Pawn";
            }
            else if(_target.charAt(0) == 'R')
            {
                _fullName += " Rook";
            }
            else if(_target.charAt(0) == 'N')
            {
                _fullName += " Knight";
            }
            else if(_target.charAt(0) == 'B')
            {
                _fullName += " Bishop";
            }
            else if(_target.charAt(0) == 'Q')
            {
                _fullName += " Queen";
            }
            else if(_target.charAt(0) == 'K')
            {
                _fullName += " King";
            }
        }
        else if(commandType == CommandType.Castle)
        {
            String tempString = _target;
            _target = tempString.substring(0,2);
            _subTarget = tempString.substring(2,4);
            tempString = _action;
            _action = tempString.substring(0,2);
            _subAction = tempString.substring(2,4);
        }
    }

    @Override
    public String toString() {
        switch(_commandType)
        {
            case Move:
                return ("Move piece from " + _target + " to " + _action + "!");
            case Place:
                return ("Place a " + _fullName + " at " + _action + "!");
            case Capture:
                return ("Move piece from " + _target + " to " + _action + ", capture!");
            case Castle:
                return ("Move king from " + _target + " to " + _subTarget + ", move rook from " + _action + " to " + _subAction + ", castle.");
            default:
                return "Command{" + "_target='" + _target + '\'' + ", _action='" + _action + '\'' + ", _commandType=" + _commandType + '}';
        }
    }

    public String get_target() {
        return _target;
    }

    public void set_target(String _target) {
        this._target = _target;
    }

    public String get_action() {
        return _action;
    }

    public void set_action(String _action) {
        this._action = _action;
    }

    public CommandType get_commandType() {
        return _commandType;
    }

    public void set_commandType(CommandType _commandType) {
        this._commandType = _commandType;
    }

    public String get_subTarget() {
        return _subTarget;
    }

    public void set_subTarget(String _subTarget) {
        this._subTarget = _subTarget;
    }

    public String get_subAction() {
        return _subAction;
    }

    public void set_subAction(String _subAction) {
        this._subAction = _subAction;
    }
}
